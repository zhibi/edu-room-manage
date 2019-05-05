package edu.room.manage.dto;

import edu.room.manage.domain.Floor;
import lombok.Data;

/**
 * @author 执笔
 * @date 2019/5/5 19:59
 */
@Data
public class FloorDTO extends Floor {

    /**
     * 楼主
     */
    private String userName;
}
