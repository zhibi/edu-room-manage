package edu.room.manage.controller.console;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.utils.ReturnUtils;
import edu.room.manage.common.annotation.Operation;
import edu.room.manage.domain.Log;
import edu.room.manage.service.LogService;
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
@RequestMapping("console/log")
public class LogController {

    @Autowired
    private LogService logService;

    @Operation("查看操作日志")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/log/index";
    }

    /**
     * 异步加载list
     *
     * @param log
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ModelMap list(Log log) {
        ModelMap      map      = new ModelMap();
        PageInfo<Log> pageInfo = logService.selectPage(log);
        map.put("pageInfo", pageInfo);
        map.put("queryParam", log);
        return ReturnUtils.success("加载成功", map);
    }
}
