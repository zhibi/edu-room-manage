package edu.room.manage.mapper;

import edu.room.manage.common.CustomerMapper;
import edu.room.manage.domain.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 执笔
 * @date 2019/5/9 11:21
 */
@Mapper
public interface LoginLogMapper extends CustomerMapper<LoginLog> {
}
