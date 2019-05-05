package edu.room.manage.service.impl;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Approval;
import edu.room.manage.dto.ApprovalDTO;
import edu.room.manage.mapper.ApprovalMapper;
import edu.room.manage.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 */
@Service
public class ApprovalServiceImpl extends BaseServiceImpl<ApprovalMapper, Approval> implements ApprovalService {

    @Autowired
    private ApprovalMapper approvalMapper;

    @Override
    public PageInfo<ApprovalDTO> selectDtoPage(MybatisCondition condition) {
        startPage(condition);
        return new PageInfo<>(approvalMapper.selectDto(condition));
    }
}