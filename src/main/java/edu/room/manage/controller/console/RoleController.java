package edu.hrm.controller.console;

import edu.hrm.common.annotation.Operation;
import edu.hrm.common.controller.BaseController;
import edu.hrm.common.exception.MessageException;
import edu.hrm.common.utils.ReturnUtils;
import edu.hrm.domain.Menu;
import edu.hrm.domain.Role;
import edu.hrm.domain.RoleMenu;
import edu.hrm.dto.MenuTree;
import edu.hrm.mapper.MenuMapper;
import edu.hrm.mapper.RoleMapper;
import edu.hrm.mapper.RoleMenuMapper;
import edu.hrm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private MenuMapper     menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;


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

    @Operation("职位授权")
    @RequestMapping(value = "/grant", method = {RequestMethod.POST})
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ModelMap grant(Integer roleId, String[] menuIds) {
        try {
            roleMenuMapper.delete(new RoleMenu().setRoleId(roleId));
            if (menuIds != null && roleId != null) {
                for (String menuId : menuIds) {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setMenuId(Integer.parseInt(menuId));
                    roleMenu.setRoleId(roleId);
                    roleMenuMapper.insertSelective(roleMenu);
                }
            }
            return ReturnUtils.success("操作成功", null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtils.error("操作失败", null, null);
        }
    }

    /**
     * 分配权限树
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/menutree", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ModelMap comboTree(Integer roleId) {
        List<Menu> menuLists = menuMapper.selectAll();
        RoleMenu   roleMenu  = new RoleMenu();
        roleMenu.setRoleId(roleId);
        List<RoleMenu>            roleMenuLists = roleMenuMapper.select(roleMenu);
        MenuTree                  menuTreeUtil  = new MenuTree(menuLists, roleMenuLists);
        List<Map<String, Object>> mapList       = menuTreeUtil.buildTree();
        return ReturnUtils.success(null, mapList, null);
    }
}
