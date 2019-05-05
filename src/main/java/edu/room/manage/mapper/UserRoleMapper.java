package edu.hrm.mapper;

import edu.hrm.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import edu.hrm.common.CustomerMapper;

/**
 * @author 执笔
 */
@Mapper
public interface UserRoleMapper extends CustomerMapper<UserRole> {
}