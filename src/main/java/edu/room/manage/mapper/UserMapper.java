package edu.room.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import edu.room.manage.common.CustomerMapper;
import edu.room.manage.domain.User;

/**
 * @author 执笔
 */
@Mapper
public interface UserMapper extends CustomerMapper<User> {
    /**
     * 根据用户名获取用户
     *
     * @param userName
     * @return
     */
    User selectByUsername(String userName);

    /**
     * 根据ID删除
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 得到用户的辅导员
     * @param userId
     * @return
     */
    User selectCounselorByUser(Integer userId);
}
