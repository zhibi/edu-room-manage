package edu.room.manage.mapper;

import edu.room.manage.common.CustomerMapper;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Message;
import edu.room.manage.dto.MessageDTO;

import java.util.List;

/**
 * @author 执笔
 * @date 2019/5/17 9:17
 */
public interface MessageMapper extends CustomerMapper<Message> {

    /**
     * 通过条件查找
     * @param condition
     * @return
     */
    List<MessageDTO> selectDto(MybatisCondition condition);
}
