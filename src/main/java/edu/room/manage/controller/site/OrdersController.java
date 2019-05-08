package edu.room.manage.controller.site;

import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.domain.Approval;
import edu.room.manage.domain.User;
import edu.room.manage.mapper.ApprovalMapper;
import edu.room.manage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private ApprovalMapper approvalMapper;
    @Autowired
    private UserMapper     userMapper;

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
}
