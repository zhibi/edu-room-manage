package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;

import javax.persistence.Column;

/**
 * 操作日志
 *
 * @author 执笔
 */
@Data
public class Log extends BaseDomain {

    private String user;

    private String ip;

    private String action;

    /**
     * 数据
     */
    @Column(columnDefinition = "text")
    private String data;

    /**
     * 用户ID
     */
    private Integer userId;
}
