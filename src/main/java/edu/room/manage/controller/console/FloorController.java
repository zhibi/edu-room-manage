package edu.room.manage.controller.console;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.exception.MessageException;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.common.utils.ReturnUtils;
import edu.room.manage.domain.Floor;
import edu.room.manage.domain.User;
import edu.room.manage.dto.FloorDTO;
import edu.room.manage.mapper.FloorMapper;
import edu.room.manage.mapper.UserMapper;
import edu.room.manage.service.FloorService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("console/floor")
public class FloorController extends BaseController {

    @Autowired
    private FloorService floorService;
    @Autowired
    private UserService  userService;

    @Operation("查看教学楼")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/floor/index";
    }


    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ModelMap list(FloorDTO floor) {
        ModelMap map = new ModelMap();
        MybatisCondition condition = new MybatisCondition()
                .like("f.name", floor.getName())
                .page(floor);
        PageInfo<FloorDTO> pageInfo = floorService.selectDtoPage(condition);
        map.put("pageInfo", pageInfo);
        return ReturnUtils.success("加载成功", map);
    }

    @Operation("教学楼详情")
    @RequestMapping(value = "/detail/{id}", method = {RequestMethod.GET})
    public String detail(@PathVariable Integer id, Model model) {
        Floor floor = floorService.selectByPrimaryKey(id);
        if (null != floor) {
        } else {
            floor = new Floor();
        }
        model.addAttribute("floor", floor);
        model.addAttribute("userList", userService.select(new User().setType(User.UserTypeEnum.LANDLORD)));
        return "console/floor/detail";
    }

    @Operation("更新教学楼信息")
    @RequestMapping(value = "/merge", method = {RequestMethod.POST})
    public String merge(@Valid Floor floor, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            throw new MessageException(result.getAllErrors().get(0).getDefaultMessage());
        }
        floorService.merge(floor);
        return redirect("/console/floor/index","修改成功", attributes);
    }


    @Operation("删除用户")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap delete(Integer id) {
        floorService.deleteByPrimaryKey(id);
        return ReturnUtils.success("删除成功", null, null);
    }
}
