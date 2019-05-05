package edu.room.manage.service.impl;

import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.domain.Room;
import edu.room.manage.mapper.LogMapper;
import edu.room.manage.service.LogService;
import edu.room.manage.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Room> implements LogService {

    @Autowired
    private LogMapper logMapper;


    @Override
    public void insertLog(User user, String ip, String action, String data) {
        Room room = new Room();
        if (null != user) {
            room.setUser(user.getUsername());
            room.setUserId(user.getId());
        }
        room.setIp(ip);
        room.setAction(action);
        room.setData(data);
        logMapper.insertSelective(room);
    }
}
