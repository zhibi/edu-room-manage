package edu.room.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import edu.room.manage.common.CustomerMapper;
import edu.room.manage.domain.Log;

/**
 * @author 执笔
 */
@Mapper
public interface LogMapper extends CustomerMapper<Log> {
}
