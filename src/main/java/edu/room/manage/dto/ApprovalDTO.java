package edu.room.manage.dto;

import edu.room.manage.domain.Approval;
import lombok.Data;

/**
 * @author 执笔
 * @date 2019/5/3 14:50
 */
@Data
public class ApprovalDTO extends Approval {
    /**
     * 员工名
     */
    private String username;

    /**
     * 员工编号
     */
    private String serialNumber;
}
