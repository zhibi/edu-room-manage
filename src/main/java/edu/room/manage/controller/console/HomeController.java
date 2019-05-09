package edu.room.manage.controller.console;

import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.context.Constant;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.utils.Md5Utils;
import edu.room.manage.domain.Admin;
import edu.room.manage.domain.Floor;
import edu.room.manage.domain.Room;
import edu.room.manage.domain.User;
import edu.room.manage.mapper.AdminMapper;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 执笔
 */
@Controller
@RequestMapping("console")
@Slf4j
public class HomeController extends BaseController {


    @Autowired
    private UserMapper  userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomMapper  roomMapper;
    @Autowired
    private FloorMapper floorMapper;
    @Autowired
    private AdminMapper adminMapper;


    /**
     * 首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "index", method = {RequestMethod.GET})

    public String index(Model model) {
        return "console/index";
    }


    /**
     * 首页展示
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "main", method = {RequestMethod.GET})
    public String right(Model model) {
        model.addAllAttributes(this.getTotal());
        return "console/right";
    }

    /**
     * 登录页
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginForm() {
        return "console/login";
    }

    @Operation("登录")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginPost(@Valid ValidUser validUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(Constant.ERROR_MESSAGE, bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:login";
        }
        String username = validUser.getUsername();
        Admin  admin    = (Admin) userService.login(validUser.getUsername(), validUser.getPassword(), User.UserRoleEnum.ADMIN);
        if (null == admin) {
            redirectAttributes.addFlashAttribute(Constant.ERROR_MESSAGE, "用户名或密码不正确");
            return "redirect:login";
        } else {
            logger.info("用户[" + username + "]登录认证通过");
            session.setAttribute(Constant.SESSION_ADMIN, admin);
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
        log.info("【退出登录】 {}", loginAdmin().getUsername());
        session.removeAttribute(SESSION_ADMIN);
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
        return "console/modify-pwd";
    }

    @Operation("修改用户密码")
    @RequestMapping(value = "/modifyPwd", method = {RequestMethod.POST})
    public String modifyPwd(String pwd, String password, String password2, RedirectAttributes attributes) {
        if (!password.equals(password2)) {
            return redirect("/console/modifyPwd", "两次密码不一样", attributes);
        }
        Admin user = adminMapper.selectByPrimaryKey(loginAdmin().getId());
        if (null != user) {
            if (!Md5Utils.encode(pwd).equalsIgnoreCase(user.getPassword())) {
                return redirect("/console/modifyPwd", "原密码错误", attributes);
            }
            String newPassword = Md5Utils.encode(password);
            user.setPassword(newPassword);
            adminMapper.updateByPrimaryKeySelective(user);
            return redirect("/console/modifyPwd", "修改成功", attributes);
        } else {
            return redirect("/console/modifyPwd", "用户不存在，修改失败", attributes);
        }
    }


    /**
     * 首页展示数据
     *
     * @return
     */
    private Map<String, Object> getTotal() {
        Integer             userCount = userMapper.selectCount(new User());
        Map<String, Object> mp        = new HashMap<>(4);
        mp.put("userCount", userCount);
        mp.put("roomCount", roomMapper.selectCount(new Room()));
        mp.put("floorCount", floorMapper.selectCount(new Floor()));
        return mp;
    }

}
