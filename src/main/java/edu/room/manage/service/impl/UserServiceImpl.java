package edu.hrm.service.impl;

import edu.hrm.common.base.service.BaseServiceImpl;
import edu.hrm.common.exception.MessageException;
import edu.hrm.common.mybatis.condition.MybatisCondition;
import edu.hrm.common.utils.PasswordUtils;
import edu.hrm.domain.User;
import edu.hrm.domain.UserRole;
import edu.hrm.mapper.UserMapper;
import edu.hrm.mapper.UserRoleMapper;
import edu.hrm.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
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
            String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
            user.setSalt(salt);
            String password = PasswordUtils.createPwd(user.getPassword(), salt);
            user.setPassword(password);
            user.setState(User.UserStateEnum.ACTIVATION);
            user.setType(User.UserTypeEnum.USER);
            user.setSerialNumber(RandomStringUtils.randomNumeric(6));
            userMapper.insertSelective(user);
        } else {
            boolean exist = isExist(new MybatisCondition().eq("username", user.getUsername()).eqNot("id", user.getId()));
            if (exist) {
                throw new MessageException("用户名已存在");
            }
            exist = isExist(new MybatisCondition().eq("serial_Number", user.getSerialNumber()).eqNot("id", user.getId()));
            User updateUser = userMapper.selectByPrimaryKey(user.getId());
            if (null != updateUser) {
                if (StringUtils.isNotBlank(user.getPassword())) {
                    String password = PasswordUtils.createPwd(user.getPassword(), updateUser.getSalt());
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
}
