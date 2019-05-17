package edu.room.manage.service.impl;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Message;
import edu.room.manage.dto.MessageDTO;
import edu.room.manage.mapper.MessageMapper;
import edu.room.manage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 * @date 2019/5/17 9:19
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message> implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public PageInfo<MessageDTO> selectDtoPage(MybatisCondition condition) {
        startPage(condition);
        return new PageInfo<>(messageMapper.selectDto(condition));
    }
}
