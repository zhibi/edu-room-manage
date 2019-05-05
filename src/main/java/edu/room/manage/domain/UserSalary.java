package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author 执笔
 * @date 2019/5/3 11:23
 */
@Data
@Table(name = "User_Salary")
public class UserSalary extends BaseDomain {

    /**
     *
     */
    private Integer userId;

    /**
     * 基本薪资
     */
    private Double  salary;
    /**
     * 加班费
     */
    private Double  overtimePay;
    /**
     * 绩效
     */
    private Double  performance;
    /**
     * 全勤奖
     */
    private Double  perfectAttendanceAward;
    /**
     * 保险费
     */
    private Double  insurance;
    /**
     * 加班次数
     */
    private Integer overtime;

    /**
     * 发放时间
     */
    private String sendTime;

    private SalaryStatusEnum status;

    public enum SalaryStatusEnum {
        /**
         * 待发送
         */
        WAIT,
        /**
         * 已发送
         */
        SENDED;
    }
}
