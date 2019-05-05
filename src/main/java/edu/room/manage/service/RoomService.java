package edu.room.manage.service;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.base.service.BaseService;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Room;
import edu.room.manage.dto.RoomDTO;

/**
 * @author 执笔
 */
public interface RoomService extends BaseService<Room> {
    /**
     * 通过条件查找
     *
     * @param condition
     * @return
     */
    PageInfo<RoomDTO> selectDtoPage(MybatisCondition condition);
}
