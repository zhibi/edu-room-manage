package edu.room.manage.controller.site;

import edu.room.manage.common.controller.BaseController;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Approval;
import edu.room.manage.domain.Floor;
import edu.room.manage.dto.RoomDTO;
import edu.room.manage.mapper.ApprovalMapper;
import edu.room.manage.mapper.FloorMapper;
import edu.room.manage.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

/**
 * @author 执笔
 * @date 2019/5/2 16:13
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private FloorMapper    floorMapper;
    @Autowired
    private RoomMapper     roomMapper;
    @Autowired
    private ApprovalMapper approvalMapper;


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
     *
     * @param floor   楼层
     * @param date    时间
     * @param scale   规格
     * @param floorId 教学楼
     * @param week    课节
     * @return
     */
    @GetMapping("search")
    public String search(String floor, String date, String scale, Integer floorId, String week, Model model) {
        // 得到符合条件的所有教室
        MybatisCondition condition = new MybatisCondition()
                .eq("r.floor_id", floorId)
                .eq("r.scale", scale)
                .eq("r.floor", floor);
        List<RoomDTO>     roomList = roomMapper.selectDto(condition);
        Iterator<RoomDTO> iterator = roomList.iterator();
        while (iterator.hasNext()) {
            RoomDTO roomDTO = iterator.next();
            // 判断能否有课时安排
            if (checkWeek(roomDTO, date, week)) {
                iterator.remove();
                continue;
            }
            // 判断是否有预约
            if (checkOrders(roomDTO, date, week)) {
                iterator.remove();
            }
        }
        model.addAttribute("roomList", roomList);
        model.addAttribute("date", date);
        model.addAttribute("week", week);
        return "site/search";
    }

    /**
     * 判断这天有没有被预约
     *
     * @param room
     * @param date
     * @param week
     * @return
     */
    private boolean checkOrders(RoomDTO room, String date, String week) {
        Approval approval = new Approval()
                .setWeek(week)
                .setRoomId(room.getId())
                .setOrderTime(date);
        int count = approvalMapper.selectCount(approval);
        return count > 0;
    }

    /**
     * 判断是否有课时安排
     *
     * @param room
     * @param date
     * @param week
     * @return
     */
    private boolean checkWeek(RoomDTO room, String date, String week) {
        // 判断日期是周几
        int value = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).getDayOfWeek().getValue();
        switch (value) {
            case 1:
                return room.getWeek1().contains(week);
            case 2:
                return room.getWeek2().contains(week);
            case 3:
                return room.getWeek3().contains(week);
            case 4:
                return room.getWeek4().contains(week);
            case 5:
                return room.getWeek5().contains(week);
            case 6:
                return room.getWeek6().contains(week);
            case 7:
                return room.getWeek7().contains(week);
            default:
        }
        return false;
    }
}
