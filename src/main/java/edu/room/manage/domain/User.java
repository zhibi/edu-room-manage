package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

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
     * 用户类型
     */
    private UserTypeEnum state;

    private UserRoleEnum role;

    /**
     * 用户名
     */
    private String name;
    private String email;
    private String phone;

    /**
     * 头像
     */
    private String icon;


    public enum UserTypeEnum {

        /**
         * 学生
         */
        STUDENT,
        /**
         * 辅导员
         */
        COUNSELOR,
        /**
         * 楼主
         */
        LANDLORD,
        /**
         * 教师
         */
        TEACHER;
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
}