package edu.room.manage.mapper;

import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Classes;
import edu.room.manage.dto.ClassesDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 执笔
 * @date 2019/5/6 9:30
 */
public interface ClassesMapper extends Mapper<Classes> {

    /**
     * 条件查找
     * @param condition
     * @return
     */
    List<ClassesDTO> selectDto(MybatisCondition condition);
}
