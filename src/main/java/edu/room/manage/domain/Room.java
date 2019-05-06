package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;

/**
 * 教室
 *
 * @author 执笔
 */
@Data
public class Room extends BaseDomain {

    private String name;

    private String address;


    /**
     * 所属教学楼
     */
    private Integer floorId;

    /**
     * 星期
     * 1 2 3 4
     */
    private String week1;
    private String week2;
    private String week3;
    private String week4;
    private String week5;
    private String week6;
    private String week7;
}
