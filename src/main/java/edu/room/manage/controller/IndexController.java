package edu.hrm.controller;

import edu.hrm.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 执笔
 * @date 2019/5/2 16:13
 */
@Controller
public class IndexController extends BaseController {

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/","index"}, method = {RequestMethod.GET})
    public String index(Model model) {
        return redirect("/console/index");
    }

}
