package edu.room.manage.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * @author 执笔
 * @date 2019/5/9 11:23
 */
@Data
@Accessors(chain = true)
@Table(name = "Orders_Log")
public class OrdersLog {

    /**
     *
     */
    private Integer ordersId;

    private Approval.ApprovalStatusEnum statusOld;


    private Approval.ApprovalStatusEnum statusNew;

    /**
     *
     */
    private Integer userId;

    /**
     * 备注
     */
    private String remark;
}
