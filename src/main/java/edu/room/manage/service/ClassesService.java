package edu.room.manage.service;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.base.service.BaseService;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Classes;
import edu.room.manage.dto.ClassesDTO;

/**
 * @author 执笔
 * @date 2019/5/6 9:31
 */
public interface ClassesService extends BaseService<Classes> {

    /**
     * 通过条件查找
     *
     * @param condition
     * @return
     */
    PageInfo<ClassesDTO> selectDtoPage(MybatisCondition condition);
}
