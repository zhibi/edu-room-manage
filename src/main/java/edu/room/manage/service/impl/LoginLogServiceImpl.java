package edu.room.manage.service.impl;

import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.domain.LoginLog;
import edu.room.manage.mapper.LoginLogMapper;
import edu.room.manage.service.LoginLogService;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 * @date 2019/5/13 18:40
 */
@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {
}
