package edu.room.manage.controller.site;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Approval;
import edu.room.manage.domain.OrdersLog;
import edu.room.manage.domain.User;
import edu.room.manage.dto.ApprovalDTO;
import edu.room.manage.service.ApprovalService;
import edu.room.manage.service.OrdersLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

/**
 * @author 执笔
 * @date 2019/5/8 15:13
 */
@Controller
@RequestMapping("orders")
@Operation(needLogin = true, value = "预约教室")
public class OrdersController extends BaseController {

    @Autowired
    private ApprovalService       approvalService;
    @Autowired
    private OrdersLogService      ordersLogService;

    /**
     * 预约教室
     *
     * @param roomId
     * @param date
     * @param week
     * @param attributes
     * @return
     */
    @GetMapping("approval/{roomId}")
    @Transactional
    public String approval(@PathVariable Integer roomId, String date, String week, RedirectAttributes attributes) {
        if (loginUser().getType() == User.UserTypeEnum.STUDENT
                || loginUser().getType() == User.UserTypeEnum.TEACHER) {
            Approval approval = new Approval()
                    .setOrderTime(date)
                    .setRoomId(roomId)
                    .setWeek(week)
                    .setStatus(Approval.ApprovalStatusEnum.WAIT)
                    .setUserId(loginUser().getId());
            approvalService.insertSelective(approval);
            ordersLogService.insertSelective(new OrdersLog().setOrdersId(approval.getId()).setUserId(loginUser().getId()).setRemark("发起预约").setStatusNew(Approval.ApprovalStatusEnum.WAIT).setStatusOld(Approval.ApprovalStatusEnum.WAIT));
            if (loginUser().getType() == User.UserTypeEnum.TEACHER) {
                // 老师预约不需要辅导员审批
                approval.setStatus(Approval.ApprovalStatusEnum.AGREE_1);
                approval.setOpinion1("自动同意");
                approvalService.updateByPrimaryKeySelective(approval);
                ordersLogService.insertSelective(new OrdersLog().setOrdersId(approval.getId()).setUserId(loginUser().getId()).setRemark("自动同意").setStatusNew(Approval.ApprovalStatusEnum.AGREE_1).setStatusOld(Approval.ApprovalStatusEnum.WAIT));
            }
            return redirect("/orders/me", "预约成功", attributes);
        } else {
            return refresh("您不是学生或者教师，不能预约", attributes);
        }
    }

    /**
     * 我的预约
     * @param model
     * @return
     */
    @GetMapping("me")
    public String me(Model model) {
        MybatisCondition condition = new MybatisCondition()
                .eq("a.user_id", loginUser().getId())
                .order("a.id", false);
        PageInfo<ApprovalDTO> pageInfo = approvalService.selectDtoPage(condition);
        model.addAttribute("ordersList", pageInfo.getList());
        return "site/orders/me";
    }

    /**
     * 我的审批
     * @param model
     * @return
     */
    @GetMapping("approval")
    public String approval(Model model) {
        MybatisCondition condition = new MybatisCondition().order("a.id", false);
        if (loginUser().getType() == User.UserTypeEnum.LANDLORD) {
            condition.eq("u2.id", loginUser().getId());
        } else {
            condition.eq("u1.id", loginUser().getId());
        }
        PageInfo<ApprovalDTO> pageInfo = approvalService.selectDtoPage(condition);
        model.addAttribute("ordersList", pageInfo.getList());
        return "site/orders/approval";
    }

    /**
     * 审批
     *
     * @param id
     * @param result
     * @param remark
     * @return
     */
    @PostMapping("sp")
    @Transactional
    public String sp(Integer id, String result, String remark, RedirectAttributes attributes) {
        Approval  approval  = approvalService.selectByPrimaryKey(id);
        OrdersLog ordersLog = new OrdersLog().setOrdersId(approval.getId()).setUserId(loginUser().getId()).setRemark(remark).setStatusOld(approval.getStatus());
        // 判断是否超时
        if (LocalDate.parse(approval.getOrderTime()).compareTo(LocalDate.now()) < 1) {
            approval.setStatus(Approval.ApprovalStatusEnum.OVER_TIME);
            approvalService.updateByPrimaryKeySelective(approval);
            ordersLogService.insertSelective(ordersLog.setStatusNew(approval.getStatus()));
            return refresh("预约已超时，不能审批", attributes);
        }
        // 楼主
        if (loginUser().getType() == User.UserTypeEnum.LANDLORD) {
            approval.setOpinion2(remark);
            approval.setStatus("1".equalsIgnoreCase(result) ? Approval.ApprovalStatusEnum.AGREE_2 : Approval.ApprovalStatusEnum.REJECT_2);
        } else {
            approval.setOpinion1(remark);
            approval.setStatus("1".equalsIgnoreCase(result) ? Approval.ApprovalStatusEnum.AGREE_1 : Approval.ApprovalStatusEnum.REJECT_1);
        }
        approvalService.updateByPrimaryKeySelective(approval);
        ordersLogService.insertSelective(ordersLog.setStatusNew(approval.getStatus()));
        return refresh("审批成功", attributes);
    }
}
