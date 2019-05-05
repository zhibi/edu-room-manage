package edu.room.manage.controller.console;

import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.exception.MessageException;
import edu.room.manage.common.utils.ReturnUtils;
import edu.room.manage.domain.Room;
import edu.room.manage.mapper.RoomMapper;
import edu.room.manage.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("/console/room")
public class RoomController extends BaseController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomMapper  roomMapper;

    @Operation("查看部门")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/room/index";
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "detail/{id}", method = {RequestMethod.GET})
    public String from(@PathVariable Integer id, @RequestParam(defaultValue = "0") Integer parentId, Model model) {
        Room room;
        if (id != 0) {
            room = roomMapper.selectByPrimaryKey(id);
        } else {
            room = new Room();
        }
        model.addAttribute("room", room);
        return "console/room/detail";
    }

    @Operation("添加更新部门职位")
    @RequestMapping(value = "/merge", method = {RequestMethod.POST})
    public String save(@Valid Room room, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            throw new MessageException(result.getAllErrors().get(0).getDefaultMessage());
        }
        roomService.merge(room);
        return redirect("/console/room/index", "操作成功", attributes);
    }

    @Operation("删除部门职位")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap delete(Integer id) {
        roomMapper.deleteByPrimaryKey(id);
        return ReturnUtils.success("操作成功", null, null);
    }


}
