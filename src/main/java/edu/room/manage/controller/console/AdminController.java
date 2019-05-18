package edu.room.manage.controller.console;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.context.Constant;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.exception.MessageException;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.common.utils.Md5Utils;
import edu.room.manage.common.utils.ReturnUtils;
import edu.room.manage.domain.Admin;
import edu.room.manage.service.AdminService;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("console/admin")
public class AdminController extends BaseController {


    @Autowired
    private AdminService adminService;

    @Operation("查看管理员列表")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/admin/index";
    }


    @Operation("管理员详情")
    @RequestMapping(value = "/detail/{id}", method = {RequestMethod.GET})
    public String detail(@PathVariable Integer id, Model model) {
        Admin admin = adminService.selectByPrimaryKey(id);
        if (null != admin) {
        } else {
            admin = new Admin();
        }
        model.addAttribute("admin", admin);
        return "console/admin/detail";
    }

    /**
     * 异步加载管理员列表
     *
     * @param admin
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(Admin admin) {
        ModelMap        map      = new ModelMap();
        PageInfo<Admin> pageInfo = adminService.selectPage(admin);
        map.put("pageInfo", pageInfo);
        return ReturnUtils.success("加载成功", map, null);
    }

    @Operation("更新管理员信息")
    @RequestMapping(value = "/merge", method = {RequestMethod.POST})
    public String merge(@Valid Admin admin, BindingResult result, RedirectAttributes attributes, MultipartFile file) {
        try {
            if (result.hasErrors()) {
                throw new MessageException(result.getAllErrors().get(0).getDefaultMessage());
            }
            admin.setType("default");
            if (admin.getId() == null && StringUtils.isBlank(admin.getPassword())) {
                throw new MessageException("请输入密码");
            }
            MybatisCondition condition = new MybatisCondition();
            condition.eq("username", admin.getUsername());
            if (admin.getId() != null) {
                condition.eqNot("id", admin.getId());
            }
            if(adminService.isExist(condition)){
                throw new MessageException("用户名已经存在");
            }

            if (StringUtils.isNotBlank(admin.getPassword())) {
                admin.setPassword(Md5Utils.encode(admin.getPassword()));
            }
            adminService.merge(admin);
            if (admin.getId().equals(loginAdmin().getId())) {
                session.setAttribute(Constant.SESSION_ADMIN, admin);
            }
            return redirect("/console/admin/index", "修改成功", attributes);
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
    }

    @Operation("删除管理员")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap delete(Integer id) {
        adminService.deleteByPrimaryKey(id);
        return ReturnUtils.success("删除成功", null, null);
    }

}
