package edu.room.manage.mapper;

import edu.room.manage.common.CustomerMapper;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.UserSalary;
import edu.room.manage.dto.UserSalaryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 执笔
 * @date 2019/5/3 12:00
 */
@Mapper
public interface UserSalaryMapper extends CustomerMapper<UserSalary> {

    /**
     * 薪资列表
     * @param mybatisCondition
     * @return
     */
    List<UserSalaryDTO> selectDto(MybatisCondition mybatisCondition);
}
