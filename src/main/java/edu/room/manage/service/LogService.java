package edu.room.manage.service;

import edu.room.manage.common.base.service.BaseService;
import edu.room.manage.domain.Room;
import edu.room.manage.domain.User;

/**
 * @author 执笔
 */
public interface LogService extends BaseService<Room> {


    /**
     * 保存操作日志
     *
     * @param user
     * @param ip
     * @param action
     */
    void insertLog(User user, String ip, String action, String data);


}
