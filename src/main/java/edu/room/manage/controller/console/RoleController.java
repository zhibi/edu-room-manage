package edu.room.manage.controller.console;

import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.exception.MessageException;
import edu.room.manage.common.utils.ReturnUtils;
import edu.room.manage.domain.Role;
import edu.room.manage.mapper.RoleMapper;
import edu.room.manage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门职位管理
 *
 * @author 执笔
 */
@Controller
@RequestMapping("/console/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService    roleService;
    @Autowired
    private RoleMapper     roleMapper;



    @Operation("查看部门")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        List<Role> roleList = roleService.getChildRoleList(new ArrayList<>(), 0);
        model.addAttribute("roleList", roleList);
        return "console/role/index";
    }

    /**
     * 部门 职位 详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "detail/{id}", method = {RequestMethod.GET})
    public String from(@PathVariable Integer id, @RequestParam(defaultValue = "0") Integer parentId, Model model) {
        Role role;
        if (id != 0) {
            role = roleMapper.selectByPrimaryKey(id);
        } else {
            role = new Role();
            role.setParentId(parentId);
        }
        model.addAttribute("role", role);
        return "console/role/detail";
    }

    @Operation("添加更新部门职位")
    @RequestMapping(value = "/merge", method = {RequestMethod.POST})
    public String save(@Valid Role role, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            throw new MessageException(result.getAllErrors().get(0).getDefaultMessage());
        }
        roleService.merge(role);
        return redirect("/console/role/index", "操作成功", attributes);
    }

    @Operation("删除部门职位")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap delete(Integer id) {
        roleMapper.deleteById(id);
        return ReturnUtils.success("操作成功", null, null);
    }

    /**
     * 授权页面
     *
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping(value = "/grant", method = {RequestMethod.GET})
    public String grantForm(String roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "console/role/grant";
    }


}
