package edu.room.manage.mapper;

import edu.room.manage.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import edu.room.manage.common.CustomerMapper;

import java.util.List;
import java.util.Set;

/**
 * @author 执笔
 */
@Mapper
public interface RoleMapper extends CustomerMapper<Role> {
    /**
     * 查找用户的角色
     *
     * @param userId
     * @return
     */
    Set<String> selectRoleByUserId(Integer userId);

    /**
     * 根据用户ID获取角色
     *
     * @param userId
     * @return
     */
    List<Role> selectRoleListByUserId(Integer userId);

    /**
     * 删除角色
     *
     * @param roleId
     */
    void deleteById(Integer roleId);
}
