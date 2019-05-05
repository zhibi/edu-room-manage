package edu.room.manage.common.base.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import edu.room.manage.common.mybatis.annotation.AutoTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 * model基础信息
 *
 * @author 执笔
 */
@Getter
@Setter
public class BaseDomain {

    /**
     * 数据库表三要素
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer       id;
    @AutoTime(insert = true, update = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;
    @AutoTime(insert = true, update = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;


    @Transient
    private Integer offset = 0;
    @Transient
    private Integer limit = 10;

}
