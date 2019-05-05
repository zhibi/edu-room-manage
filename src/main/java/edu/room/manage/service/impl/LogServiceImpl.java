package edu.room.manage.service.impl;

import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.domain.Log;
import edu.room.manage.mapper.LogMapper;
import edu.room.manage.service.LogService;
import edu.room.manage.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log> implements LogService {

    @Autowired
    private LogMapper logMapper;


    @Override
    public void insertLog(User user, String ip, String action, String data) {
        Log log = new Log();
        if (null != user) {
            log.setUser(user.getUsername());
            log.setUserId(user.getId());
        }
        log.setIp(ip);
        log.setAction(action);
        log.setData(data);
        logMapper.insertSelective(log);
    }
}
