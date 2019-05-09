package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 执笔
 * @date 2019/5/9 11:11
 */
@Data
@Accessors(chain = true)
public class Admin extends BaseDomain implements Serializable {

    @NotEmpty(message = "账号不能为空")
    private String username;

    private String password;
}
