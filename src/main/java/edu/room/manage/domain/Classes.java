package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;

/**
 * @author 执笔
 * @date 2019/5/6 9:29
 */
@Data
public class Classes extends BaseDomain {

    /**
     * 辅导员ID
     */
    private Integer userId;

    /**
     * 名字
     */
    private String name;
}
