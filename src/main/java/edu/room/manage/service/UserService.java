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
     */
    void updateOrSaveUser(User user);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param role     用户类型
     * @return
     */
    Object login(String username, String password, User.UserRoleEnum role);

}
