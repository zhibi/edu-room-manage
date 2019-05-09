package edu.room.manage.controller.site;

import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.context.Constant;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.common.utils.Md5Utils;
import edu.room.manage.domain.Approval;
import edu.room.manage.domain.Floor;
import edu.room.manage.domain.User;
import edu.room.manage.dto.RoomDTO;
import edu.room.manage.mapper.ApprovalMapper;
import edu.room.manage.mapper.FloorMapper;
import edu.room.manage.mapper.RoomMapper;
import edu.room.manage.mapper.UserMapper;
import edu.room.manage.service.UserService;
import edu.room.manage.valid.ValidUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

/**
 * @author 执笔
 * @date 2019/5/2 16:13
 */
@Controller
@Slf4j
public class IndexController extends BaseController {

    @Autowired
    private FloorMapper    floorMapper;
    @Autowired
    private RoomMapper     roomMapper;
    @Autowired
    private ApprovalMapper approvalMapper;
    @Autowired
    private UserMapper     userMapper;
    @Autowired
    private UserService    userService;

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/", "index"}, method = {RequestMethod.GET})
    public String index(Model model) {
        List<Floor> floorList = floorMapper.selectAll();
        model.addAttribute("floorList", floorList);
        return "site/index";
    }

    /**
     * 预约搜索
     *
     * @param floor   楼层
     * @param date    时间
     * @param scale   规格
     * @param floorId 教学楼
     * @param week    课节
     * @return
     */
    @GetMapping("search")
    public String search(String floor, String date, String scale, Integer floorId, String week, Model model) {
        // 得到符合条件的所有教室
        MybatisCondition condition = new MybatisCondition()
                .eq("r.floor_id", floorId)
                .eq("r.scale", scale)
                .eq("r.floor", floor);
        List<RoomDTO>     roomList = roomMapper.selectDto(condition);
        Iterator<RoomDTO> iterator = roomList.iterator();
        while (iterator.hasNext()) {
            RoomDTO roomDTO = iterator.next();
            // 判断能否有课时安排
            if (checkWeek(roomDTO, date, week)) {
                iterator.remove();
                continue;
            }
            // 判断是否有预约
            if (checkOrders(roomDTO, date, week)) {
                iterator.remove();
            }
        }
        model.addAttribute("roomList", roomList);
        model.addAttribute("date", date);
        model.addAttribute("week", week);
        return "site/search";
    }

    /**
     * 登录页
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginForm() {
        return "site/login";
    }

    @Operation("登录")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginPost(@Valid ValidUser validUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(Constant.ERROR_MESSAGE, bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:login";
        }
        String username = validUser.getUsername();
        User   user     = (User) userService.login(validUser.getUsername(), validUser.getPassword(), User.UserRoleEnum.USER);
        if (null == user) {
            redirectAttributes.addFlashAttribute(Constant.ERROR_MESSAGE, "用户名或密码不正确");
            return "redirect:login";
        } else {
            logger.info("用户[" + username + "]登录认证通过");
            session.setAttribute(Constant.SESSION_USER, user);
            return "redirect:index";
        }
    }

    /**
     * 退出登录
     *
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes) {
        log.info("【退出登录】 {}", loginUser().getUsername());
        session.removeAttribute(SESSION_USER);
        redirectAttributes.addFlashAttribute(Constant.ERROR_MESSAGE, "您已安全退出");
        return "redirect:login";
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = "/modifyPwd", method = {RequestMethod.GET})
    public String modifyPwd() {
        return "site/modify-pwd";
    }

    @Operation("修改用户密码")
    @RequestMapping(value = "/modifyPwd", method = {RequestMethod.POST})
    public String modifyPwd(String pwd, String password, String password2, RedirectAttributes attributes) {
        if (!password.equals(password2)) {
            return redirect("/modifyPwd", "两次密码不一样", attributes);
        }
        User user = userMapper.selectByPrimaryKey(loginUser().getId());
        if (null != user) {
            if (!Md5Utils.encode(pwd).equalsIgnoreCase(user.getPassword())) {
                return redirect("/modifyPwd", "原密码错误", attributes);
            }
            String newPassword = Md5Utils.encode(password);
            user.setPassword(newPassword);
            userMapper.updateByPrimaryKeySelective(user);
            return redirect("/modifyPwd", "修改成功", attributes);
        } else {
            return redirect("/modifyPwd", "用户不存在，修改失败", attributes);
        }
    }

    /**
     * 个人信息
     *
     * @return
     */
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    @Operation(value = "个人信息", needLogin = true)
    public String info(Model model) {
        model.addAttribute("user", userMapper.selectByPrimaryKey(loginUser().getId()));
        return "site/info";
    }

    @Operation(value = "修改用户信息", needLogin = true)
    @RequestMapping(value = "/updateInfo", method = {RequestMethod.POST})
    public String updateInfo(User user, RedirectAttributes attributes) {
        userMapper.updateByPrimaryKeySelective(user);
        return refresh("修改成功", attributes);
    }


    /**
     * 判断这天有没有被预约
     *
     * @param room
     * @param date
     * @param week
     * @return
     */
    private boolean checkOrders(RoomDTO room, String date, String week) {
        Approval approval = new Approval()
                .setWeek(week)
                .setRoomId(room.getId())
                .setOrderTime(date);
        int count = approvalMapper.selectCount(approval);
        return count > 0;
    }

    /**
     * 判断是否有课时安排
     *
     * @param room
     * @param date
     * @param week
     * @return
     */
    private boolean checkWeek(RoomDTO room, String date, String week) {
        // 判断日期是周几
        int value = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).getDayOfWeek().getValue();
        switch (value) {
            case 1:
                return room.getWeek1().contains(week);
            case 2:
                return room.getWeek2().contains(week);
            case 3:
                return room.getWeek3().contains(week);
            case 4:
                return room.getWeek4().contains(week);
            case 5:
                return room.getWeek5().contains(week);
            case 6:
                return room.getWeek6().contains(week);
            case 7:
                return room.getWeek7().contains(week);
            default:
        }
        return false;
    }
}
