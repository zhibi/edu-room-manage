package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;

import javax.persistence.Column;

/**
 * 教室
 *
 * @author 执笔
 */
@Data
public class Room extends BaseDomain {

    private String name;

    private String address;

    private String action;

    /**
     * 数据
     */
    @Column(columnDefinition = "text")
    private String data;

    /**
     * 辅导员ID
     */
    private Integer userId;
}
