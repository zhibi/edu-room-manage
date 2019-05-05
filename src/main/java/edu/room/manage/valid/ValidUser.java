package edu.room.manage.valid;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 执笔
 */
@Data
public class ValidUser {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

}
