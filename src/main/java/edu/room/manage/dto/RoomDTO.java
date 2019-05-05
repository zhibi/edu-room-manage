package edu.room.manage.dto;

import edu.room.manage.domain.Room;
import lombok.Data;

/**
 * @author 执笔
 * @date 2019/5/5 20:50
 */
@Data
public class RoomDTO extends Room {

    /**
     * 辅导员
     */
    private String userName;

    /**
     * 教学楼
     */
    private String floorName;
}
