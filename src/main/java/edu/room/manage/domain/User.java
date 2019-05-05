package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author 执笔
 */
@Data
@Accessors(chain = true)
public class User extends BaseDomain implements Serializable {

    @NotEmpty(message = "账号不能为空")
    private String username;

    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 用户状态
     */
    private UserStateEnum state;

    private UserRoleEnum role;


    // 个人信息
    /**
     * 用户名
     */
    private String name;
    private String email;
    private String phone;
    /**
     * 员工编号
     */
    private String serialNumber;

    /**
     * 头像
     */
    private String icon;

    /**
     * 基本薪资
     */
    private Double salary;


    public enum UserStateEnum {

        /**
         * 激活
         */
        ACTIVATION,
        /**
         * 锁定
         */
        LOCKING;
    }

    public enum UserRoleEnum {

        /**
         * 管理员
         */
        ADMIN,
        /**
         * 用户
         */
        USER;
    }


    @Transient
    private List<Role> roleList;
}