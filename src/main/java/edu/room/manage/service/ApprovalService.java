package edu.room.manage.service;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.base.service.BaseService;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Approval;
import edu.room.manage.dto.ApprovalDTO;

/**
 * @author 执笔
 */
public interface ApprovalService extends BaseService<Approval> {

    /**
     * 列表
     * @param condition
     * @return
     */
    PageInfo<ApprovalDTO> selectDtoPage(MybatisCondition condition);
}
