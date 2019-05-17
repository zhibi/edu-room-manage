package edu.room.manage.dto;

import edu.room.manage.domain.Message;
import lombok.Data;

/**
 * @author 执笔
 * @date 2019/5/17 9:22
 */
@Data
public class MessageDTO extends Message {

    /**
     *
     */
    private String userName;

    /**
     *
     */
    private String roomName;

    /**
     *
     */
    private String floorName;
}
