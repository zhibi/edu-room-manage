package edu.hrm.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import edu.hrm.common.base.dto.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author 执笔
 */
@Table(name = "user_role")
@Data
@Accessors(chain = true)
public class UserRole extends BaseDomain {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;
}