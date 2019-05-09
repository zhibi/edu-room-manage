package edu.room.manage.mapper;

import edu.room.manage.common.CustomerMapper;
import edu.room.manage.domain.OrdersOvertime;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 执笔
 * @date 2019/5/9 13:34
 */
@Mapper
public interface OrdersOvertimeMapper extends CustomerMapper<OrdersOvertime> {
}
