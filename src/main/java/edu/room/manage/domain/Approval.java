package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;

/**
 * @author 执笔
 * @date 2019/5/3 14:45
 */
@Data
public class Approval extends BaseDomain {

    /**
     *
     */
    private Integer userId;

    /**
     * 预约教室
     */
    private Integer roomId;

    /**
     * 预约时间
     */
    private String orderTime;

    /**
     * 一级审批意见
     */
    private String opinion1;
    /**
     * 二级审批意见
     */
    private String opinion2;

    private ApprovalStatusEnum status;


    public enum ApprovalStatusEnum {
        /**
         * 待审核
         */
        WAIT,
        /**
         * 同意
         */
        AGREE_1,
        /**
         * 拒绝
         */
        REJECT_1,
        AGREE_2,
        /**
         * 拒绝
         */
        REJECT_2;
    }
}