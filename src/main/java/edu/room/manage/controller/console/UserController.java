package edu.room.manage.controller.console;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.context.Constant;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.exception.MessageException;
import edu.room.manage.common.utils.ReturnUtils;
import edu.room.manage.domain.User;
import edu.room.manage.mapper.ClassesMapper;
import edu.room.manage.mapper.UserMapper;
import edu.room.manage.service.FileUploadService;
import edu.room.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("console/user")
public class UserController extends BaseController {


    @Autowired
    private UserService       userService;
    @Autowired
    private UserMapper        userMapper;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private ClassesMapper     classesMapper;

    @Operation("查看用户列表")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/user/index";
    }


    @Operation("用户详情")
    @RequestMapping(value = "/detail/{id}", method = {RequestMethod.GET})
    public String detail(@PathVariable Integer id, Model model) {
        User user = userMapper.selectByPrimaryKey(id);
        if (null != user) {
        } else {
            user = new User();
        }
        model.addAttribute("classesList", classesMapper.selectAll());
        model.addAttribute("user", user);
        return "console/user/detail";
    }

    /**
     * 异步加载用户列表
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(User user) {
        ModelMap       map      = new ModelMap();
        PageInfo<User> pageInfo = userService.selectPage(user);
        map.put("pageInfo", pageInfo);
        return ReturnUtils.success("加载成功", map, null);
    }

    @Operation("更新用户信息")
    @RequestMapping(value = "/merge", method = {RequestMethod.POST})
    public String merge(@Valid User user, BindingResult result, RedirectAttributes attributes, MultipartFile file) {
        try {
            if (result.hasErrors()) {
                throw new MessageException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (file != null && !file.isEmpty()) {
                String path = fileUploadService.upload(file, "upload/user_icon");
                user.setIcon(path);
            }
            userService.updateOrSaveUser(user);
            if (user.getId().equals(loginAdmin().getId())) {
                session.setAttribute(Constant.SESSION_USER, user);
            }
            return redirect("/console/user/index", "修改成功", attributes);
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
    }

    @Operation("删除用户")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap delete(Integer id) {
        userMapper.deleteById(id);
        return ReturnUtils.success("删除成功", null, null);
    }

}
