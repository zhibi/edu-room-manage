package edu.room.manage.mapper;

import edu.room.manage.common.utils.Md5Utils;
import edu.room.manage.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
                .setPassword(Md5Utils.encode("123456"))
                .setUsername("admin");
        userMapper.insertSelective(user);
    }

}