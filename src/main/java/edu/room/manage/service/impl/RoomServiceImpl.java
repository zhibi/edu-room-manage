package edu.room.manage.service.impl;

import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.domain.Room;
import edu.room.manage.mapper.RoomMapper;
import edu.room.manage.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 */
@Service
public class RoomServiceImpl extends BaseServiceImpl<RoomMapper, Room> implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

}
