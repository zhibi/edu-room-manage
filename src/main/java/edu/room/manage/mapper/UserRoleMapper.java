package edu.room.manage.mapper;

import edu.room.manage.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import edu.room.manage.common.CustomerMapper;

/**
 * @author 执笔
 */
@Mapper
public interface UserRoleMapper extends CustomerMapper<UserRole> {
}