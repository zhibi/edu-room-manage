package edu.room.manage.controller.site;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.annotation.Operation;
import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.common.utils.ReturnUtils;
import edu.room.manage.domain.Message;
import edu.room.manage.domain.Room;
import edu.room.manage.dto.MessageDTO;
import edu.room.manage.dto.RoomDTO;
import edu.room.manage.service.FloorService;
import edu.room.manage.service.MessageService;
import edu.room.manage.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author 执笔
 * @date 2019/5/17 9:21
 */
@Controller
@RequestMapping("message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private FloorService   floorService;
    @Autowired
    private RoomService    roomService;

    @Operation("查看留言列表")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        MybatisCondition condition = new MybatisCondition()
                .eq("f.user_id", loginUser().getId())
                .order("m.id", false);
        PageInfo<MessageDTO> pageInfo = messageService.selectDtoPage(condition);
        model.addAttribute(pageInfo);
        return "site/message/index";
    }


    /**
     * 留言
     */
    @GetMapping("send")
    public String send(Model model) {
        model.addAttribute("floorList", floorService.selectAll());
        return "site/message/send";
    }

    /**
     * 提交留言
     *
     * @param message
     * @param attributes
     * @return
     */
    @PostMapping("send")
    public String send(Message message, RedirectAttributes attributes) {
        message.setUserId(loginUser().getId());
        messageService.insertSelective(message);
        return refresh("提交成功", attributes);
    }

    @ResponseBody
    @RequestMapping(value = "/roomList", method = {RequestMethod.GET})
    public ModelMap list(RoomDTO room) {
        ModelMap map = new ModelMap();
        map.put("list", roomService.select(new Room().setFloorId(room.getFloorId())));
        return ReturnUtils.success("加载成功", map);
    }

}
