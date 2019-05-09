package edu.room.manage.mapper;

import edu.room.manage.common.CustomerMapper;
import edu.room.manage.domain.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 执笔
 * @date 2019/5/9 11:13
 */
@Mapper
public interface AdminMapper extends CustomerMapper<Admin> {
}
