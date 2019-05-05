package edu.room.manage.service.impl;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.UserSalary;
import edu.room.manage.dto.UserSalaryDTO;
import edu.room.manage.mapper.UserSalaryMapper;
import edu.room.manage.service.UserSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 */
@Service
public class UserSalaryServiceImpl extends BaseServiceImpl<UserSalaryMapper, UserSalary> implements UserSalaryService {

    @Autowired
    private UserSalaryMapper userSalaryMapper;

    @Override
    public PageInfo<UserSalaryDTO> selectDtoPage(MybatisCondition condition) {
        startPage(condition);
        return new PageInfo<>(userSalaryMapper.selectDto(condition));
    }
}