package edu.room.manage.mapper;

import edu.room.manage.common.CustomerMapper;
import edu.room.manage.domain.Floor;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 执笔
 */
@Mapper
public interface FloorMapper extends CustomerMapper<Floor> {
}
