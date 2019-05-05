package edu.hrm.domain;

import edu.hrm.common.base.dto.BaseDomain;
import lombok.Data;

import javax.persistence.Column;

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
     * 审批类型
     */
    private String type;

    /**
     * 备注
     */
    @Column(columnDefinition = "text")
    private String remark;

    /**
     * 审批意见
     */
    private String opinion;

    private ApprovalStatusEnum status;


    public enum ApprovalStatusEnum {
        /**
         * 待审核
         */
        WAIT,
        /**
         * 同意
         */
        AGREE,
        /**
         * 拒绝
         */
        REJECT;
    }
}
