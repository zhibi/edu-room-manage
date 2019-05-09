package edu.room.manage.controller.site;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Approval;
import edu.room.manage.domain.User;
import edu.room.manage.dto.ApprovalDTO;
import edu.room.manage.mapper.ApprovalMapper;
import edu.room.manage.mapper.UserMapper;
import edu.room.manage.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author 执笔
 * @date 2019/5/8 15:13
 */
@Controller
@RequestMapping("orders")
@Operation(needLogin = true, value = "预约教室")
public class OrdersController extends BaseController {

    @Autowired
    private ApprovalMapper  approvalMapper;
    @Autowired
    private UserMapper      userMapper;
    @Autowired
    private ApprovalService approvalService;

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
    public String approval(@PathVariable Integer roomId, String date, String week, RedirectAttributes attributes) {
        if (loginUser().getType() == User.UserTypeEnum.STUDENT
                || loginUser().getType() == User.UserTypeEnum.TEACHER) {
            Approval approval = new Approval()
                    .setOrderTime(date)
                    .setRoomId(roomId)
                    .setWeek(week)
                    .setStatus(Approval.ApprovalStatusEnum.WAIT)
                    .setUserId(loginUser().getId());
            approvalMapper.insertSelective(approval);
            return redirect("/orders/me", "预约成功", attributes);
        } else {
            return refresh("您不是学生或者教师，不能预约", attributes);
        }
    }

    @GetMapping("me")
    public String me(Model model) {
        MybatisCondition condition = new MybatisCondition()
                .eq("a.user_id", loginUser().getId())
                .order("a.id", false);
        PageInfo<ApprovalDTO> pageInfo = approvalService.selectDtoPage(condition);
        model.addAttribute("ordersList", pageInfo.getList());
        return "site/orders/me";
    }

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
    public String sp(Integer id, String result, String remark, RedirectAttributes attributes) {
        Approval approval = approvalMapper.selectByPrimaryKey(id);
        // 楼主
        if (loginUser().getType() == User.UserTypeEnum.LANDLORD) {
            approval.setOpinion2(remark);
            approval.setStatus("1".equalsIgnoreCase(result) ? Approval.ApprovalStatusEnum.AGREE_2 : Approval.ApprovalStatusEnum.REJECT_2);
        } else {
            approval.setOpinion1(remark);
            approval.setStatus("1".equalsIgnoreCase(result) ? Approval.ApprovalStatusEnum.AGREE_1 : Approval.ApprovalStatusEnum.REJECT_1);
        }
        approvalMapper.updateByPrimaryKeySelective(approval);
        return refresh("审批成功", attributes);
    }
}
