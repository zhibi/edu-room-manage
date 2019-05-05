package edu.hrm.domain;

import edu.hrm.common.base.dto.BaseDomain;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

/**
 * @author 执笔
 */
@Data
@Accessors(chain = true)
public class Menu extends BaseDomain {


    @NotEmpty(message = "菜单名称不能为空")
    private String name;

    @Column
    private MenuTypeStatus type;

    @NotEmpty(message = "菜单URL不能为空")
    private String url;

    @NotEmpty(message = "菜单标识不能为空")
    private String code;

    private Integer parentId;

    private Integer childNum;

    private Integer sort;

    /**
     * 图标
     * fa fa-dashboard
     */
    private String icon;


    public enum MenuTypeStatus {
        /**
         * 菜单显示
         */
        MENU,
        /**
         * 权限
         */
        AUTH,
        /**
         * 按钮
         */
        BUTTON;
    }

}