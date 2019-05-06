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
     * 发起人
     */
    private String userName;

    /**
     * 一级审批人
     */
    private String opinionUser1;

    /**
     * 二级审批人
     */
    private String opinionUser2;

    /**
     * 教学楼
     */
    private String floorName;

    /**
     * 教室
     */
    private String roomName;
}
