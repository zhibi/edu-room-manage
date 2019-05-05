package edu.room.manage.service;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.base.service.BaseService;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.UserSalary;
import edu.room.manage.dto.UserSalaryDTO;

/**
 * @author 执笔
 */
public interface UserSalaryService extends BaseService<UserSalary> {

    /**
     * 薪资列表
     * @param condition
     * @return
     */
    PageInfo<UserSalaryDTO> selectDtoPage(MybatisCondition condition);
}
