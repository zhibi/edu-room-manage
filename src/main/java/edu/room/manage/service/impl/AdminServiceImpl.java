package edu.room.manage.service.impl;

import edu.room.manage.common.base.service.BaseService;
import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.domain.Admin;
import edu.room.manage.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 * @date 2019/5/13 18:36
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<AdminMapper, Admin> implements BaseService<Admin> {
}
