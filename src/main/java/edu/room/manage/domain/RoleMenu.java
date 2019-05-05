package edu.hrm.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import edu.hrm.common.base.dto.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author 执笔
 */
@Data
@Table(name = "role_menu")
@Accessors(chain = true)
public class RoleMenu extends BaseDomain {
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "menu_id")
    private Integer menuId;

}