package edu.room.manage.controller.console;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.utils.ReturnUtils;
import edu.room.manage.domain.Floor;
import edu.room.manage.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("console/floor")
public class FloorController extends BaseController {

    @Autowired
    private FloorService floorService;

    @Operation("查看操作日志")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/floor/index";
    }


    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ModelMap list(Floor floor) {
        ModelMap       map      = new ModelMap();
        PageInfo<Floor> pageInfo = floorService.selectPage(floor);
        map.put("pageInfo", pageInfo);
        return ReturnUtils.success("加载成功", map);
    }
}
