package edu.room.manage.service;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.base.service.BaseService;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Floor;
import edu.room.manage.dto.FloorDTO;

/**
 * @author 执笔
 */
public interface FloorService extends BaseService<Floor> {

    /**
     * 通过条件查找
     *
     * @param condition
     * @return
     */
    PageInfo<FloorDTO> selectDtoPage(MybatisCondition condition);
}
