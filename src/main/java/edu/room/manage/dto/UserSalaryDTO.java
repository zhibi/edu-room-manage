package edu.room.manage.dto;

import edu.room.manage.domain.UserSalary;
import lombok.Data;

/**
 * @author 执笔
 * @date 2019/5/3 11:23
 */
@Data
public class UserSalaryDTO extends UserSalary {

    /**
     * 员工名
     */
    private String username;

    /**
     * 员工编号
     */
    private String serialNumber;
}
