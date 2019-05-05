package edu.hrm.mapper;

import org.apache.ibatis.annotations.Mapper;
import edu.hrm.common.CustomerMapper;
import edu.hrm.domain.Log;

/**
 * @author 执笔
 */
@Mapper
public interface LogMapper extends CustomerMapper<Log> {
}
