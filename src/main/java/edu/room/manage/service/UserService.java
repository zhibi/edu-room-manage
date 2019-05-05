package edu.room.manage.service;

import edu.room.manage.common.base.service.BaseService;
import edu.room.manage.domain.User;

/**
 * @author 执笔
 */
public interface UserService extends BaseService<User> {


    /**
     * 更新或者保存用户
     *
     * @param user
     * @param split
     */
    void updateOrSaveUser(User user, String[] split);
}
