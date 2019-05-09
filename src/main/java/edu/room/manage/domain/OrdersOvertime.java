package edu.room.manage.domain;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 执笔
 * @date 2019/5/9 13:27
 */
@Data
@Accessors(chain = true)
@Table(name = "Orders_Overtime")
public class OrdersOvertime extends BaseDomain implements Serializable {

    /**
     *
     */
    private Integer ordersId;
}
