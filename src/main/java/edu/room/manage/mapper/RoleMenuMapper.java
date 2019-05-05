package edu.room.manage.mapper;

import edu.room.manage.domain.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import edu.room.manage.common.CustomerMapper;

/**
 * @author 执笔
 */
@Mapper
public interface RoleMenuMapper extends CustomerMapper<RoleMenu> {
}