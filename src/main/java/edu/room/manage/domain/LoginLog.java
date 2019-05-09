package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 执笔
 * @date 2019/5/9 11:18
 */
@Data
@Accessors(chain = true)
@Table(name = "Login_Log")
public class LoginLog extends BaseDomain implements Serializable {

    /**
     *
     */
    private String username;

    /**
     *
     */
    private Integer userId;

    /**
     *
     */
    private String ip;
}
