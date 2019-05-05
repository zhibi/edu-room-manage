package edu.room.manage.mapper;

import edu.room.manage.common.CustomerMapper;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Floor;
import edu.room.manage.dto.FloorDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 执笔
 */
@Mapper
public interface FloorMapper extends CustomerMapper<Floor> {

    /**
     *
     * @param condition
     * @return
     */
    List<FloorDTO> selectDto(MybatisCondition condition);
}
