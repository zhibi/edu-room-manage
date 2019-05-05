package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 部门 && 职位
 *
 * @author 执笔
 */
@Data
public class Role extends BaseDomain {

    @NotEmpty(message = "名称不能为空")
    private String name;

    /**
     * 上级ID
     */
    private Integer parentId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否可用
     */
    private Boolean enable;


}
