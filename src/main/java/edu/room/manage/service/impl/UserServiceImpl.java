package edu.room.manage.service.impl;

import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.common.exception.MessageException;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.common.utils.Md5Utils;
import edu.room.manage.domain.User;
import edu.room.manage.domain.UserRole;
import edu.room.manage.mapper.UserMapper;
import edu.room.manage.mapper.UserRoleMapper;
import edu.room.manage.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 执笔
 * @date 2019/5/1 22:57
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper     userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrSaveUser(User user, String[] roleIds) {
        if (user.getId() == null) {
            boolean exist = isExist(new MybatisCondition().eq("username", user.getUsername()));
            if (exist) {
                throw new MessageException("用户名已存在");
            }
            if (StringUtils.isEmpty(user.getPassword())) {
                throw new MessageException("密码不能为空");
            }
            String password = Md5Utils.encode(user.getPassword());
            user.setPassword(password);
            user.setState(User.UserStateEnum.ACTIVATION);
            user.setRole(User.UserRoleEnum.USER);
            user.setSerialNumber(RandomStringUtils.randomNumeric(6));
            userMapper.insertSelective(user);
        } else {
            boolean exist = isExist(new MybatisCondition().eq("username", user.getUsername()).eqNot("id", user.getId()));
            if (exist) {
                throw new MessageException("用户名已存在");
            }
            User updateUser = userMapper.selectByPrimaryKey(user.getId());
            if (null != updateUser) {
                if (StringUtils.isNotBlank(user.getPassword())) {
                    String password = Md5Utils.encode(user.getPassword());
                    user.setPassword(password);
                } else {
                    user.setPassword(null);
                }
                userMapper.updateByPrimaryKeySelective(user);
            } else {
                throw new MessageException("操作失败，用户不存在");
            }
        }
        userRoleMapper.delete(new UserRole().setUserId(user.getId()));
        if (roleIds != null) {
            userRoleMapper.delete(new UserRole().setUserId(user.getId()));
            for (String roleId : roleIds) {
                UserRole userRole = new UserRole()
                        .setUserId(user.getId())
                        .setRoleId(Integer.parseInt(roleId));
                userRoleMapper.insertSelective(userRole);
            }
        }
    }

    @Override
    public User login(String username, String password, User.UserRoleEnum role) {
        User user = new User();
        user.setRole(role);
        user.setUsername(username);
        user.setPassword(Md5Utils.encode(password));
        return userMapper.selectOne(user);
    }
}
