package edu.room.manage.mapper;

import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Room;
import edu.room.manage.dto.RoomDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 执笔
 * @date 2019/5/5 18:49
 */
public interface RoomMapper extends Mapper<Room> {

    /**
     * 根据条件查找
     * @param condition
     * @return
     */
    List<RoomDTO> selectDto(MybatisCondition condition);
}
