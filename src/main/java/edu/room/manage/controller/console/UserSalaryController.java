package edu.room.manage.controller.console;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.exception.MessageException;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.common.utils.ReturnUtils;
import edu.room.manage.domain.User;
import edu.room.manage.domain.UserSalary;
import edu.room.manage.dto.UserSalaryDTO;
import edu.room.manage.mapper.UserMapper;
import edu.room.manage.mapper.UserSalaryMapper;
import edu.room.manage.service.UserSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * 薪资管理
 *
 * @author 执笔
 */
@Controller
@RequestMapping("console/userSalary")
public class UserSalaryController extends BaseController {


    @Autowired
    private UserMapper        userMapper;
    @Autowired
    private UserSalaryService userSalaryService;
    @Autowired
    private UserSalaryMapper  userSalaryMapper;

    @Operation("查看薪资列表")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/salary/index";
    }


    @Operation("薪资详情")
    @RequestMapping(value = "/detail/{id}", method = {RequestMethod.GET})
    public String detail(@PathVariable Integer id, Model model) {
        UserSalary userSalary = userSalaryMapper.selectByPrimaryKey(id);
        if (null != userSalary) {
        } else {
            userSalary = new UserSalary();
        }
        List<User> userList = userMapper.selectAll();
        model.addAttribute("userList", userList);
        model.addAttribute("userSalary", userSalary);
        return "console/salary/detail";
    }

    /**
     * 异步加载薪资列表
     *
     * @param userSalary
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(UserSalaryDTO userSalary) {
        ModelMap map = new ModelMap();
        MybatisCondition condition = new MybatisCondition()
                .eq("u.id",userSalary.getUserId())
                .like("u.name", userSalary.getUsername())
                .like("u.Serial_Number", userSalary.getSerialNumber())
                .order("us.id", false);
        PageInfo<UserSalaryDTO> pageInfo = userSalaryService.selectDtoPage(condition);
        map.put("pageInfo", pageInfo);
        return ReturnUtils.success("加载成功", map, null);
    }

    @Operation("更新薪资")
    @RequestMapping(value = "/merge", method = {RequestMethod.POST})
    public String merge(@Valid UserSalary userSalary, BindingResult result, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                throw new MessageException(result.getAllErrors().get(0).getDefaultMessage());
            }
            userSalaryService.merge(userSalary);
            return redirect("/console/userSalary/index", "操作成功", attributes);
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
    }

}
