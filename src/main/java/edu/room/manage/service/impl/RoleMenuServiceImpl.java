package edu.room.manage.service.impl;

import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.domain.RoleMenu;
import edu.room.manage.mapper.RoleMenuMapper;
import edu.room.manage.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {}
