package edu.room.manage.controller.site;

import edu.room.manage.common.controller.BaseController;
import edu.room.manage.domain.Floor;
import edu.room.manage.mapper.FloorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author 执笔
 * @date 2019/5/2 16:13
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private FloorMapper floorMapper;

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
     * @param floor
     * @param date
     * @param scale
     * @param floorId
     * @param week
     * @return
     */
    @GetMapping("search")
    public String search(String floor,String date,String scale,Integer floorId,Integer week){
        return "site/search";
    }

}
