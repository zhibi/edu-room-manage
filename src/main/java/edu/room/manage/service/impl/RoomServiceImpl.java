package edu.room.manage.service.impl;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Room;
import edu.room.manage.dto.RoomDTO;
import edu.room.manage.mapper.RoomMapper;
import edu.room.manage.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 执笔
 */
@Service
public class RoomServiceImpl extends BaseServiceImpl<RoomMapper, Room> implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public PageInfo<RoomDTO> selectDtoPage(MybatisCondition condition) {
        startPage(condition);
        return new PageInfo<>(roomMapper.selectDto(condition));
    }

    @Override
    public List<RoomDTO> selectDto(MybatisCondition condition) {
        return roomMapper.selectDto(condition);
    }

}
