package edu.room.manage.service.impl;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Classes;
import edu.room.manage.dto.ClassesDTO;
import edu.room.manage.mapper.ClassesMapper;
import edu.room.manage.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 * @date 2019/5/6 9:32
 */
@Service
public class ClassesServiceImpl extends BaseServiceImpl<ClassesMapper, Classes> implements ClassesService {
    @Autowired
    private ClassesMapper classesMapper;

    @Override
    public PageInfo<ClassesDTO> selectDtoPage(MybatisCondition condition) {
        startPage(condition);
        return new PageInfo<>(classesMapper.selectDto(condition));
    }
}
