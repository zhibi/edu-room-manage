package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 教学楼
 *
 * @author 执笔
 */
@Data
public class Floor extends BaseDomain {

    @NotEmpty(message = "名称不能为空")
    private String name;

    /**
     * 楼长
     */
    private Integer userId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 位置
     */
    private String address;

}
