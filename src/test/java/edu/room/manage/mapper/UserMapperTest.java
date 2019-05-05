package edu.room.manage.mapper;

import edu.room.manage.common.utils.PasswordUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import edu.room.manage.domain.User;

/**
 * @author 执笔
 * @date 2019/5/2 16:35
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertAdmin() {
        User user = new User()
                .setType(User.UserTypeEnum.ADMIN)
                .setState(User.UserStateEnum.ACTIVATION)
                .setSalt("zhibi")
                .setPassword(PasswordUtils.createPwd("123456", "zhibi"))
                .setUsername("admin");
        userMapper.insertSelective(user);
    }

}