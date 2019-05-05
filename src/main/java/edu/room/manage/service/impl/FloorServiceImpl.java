package edu.room.manage.service.impl;

import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.domain.Floor;
import edu.room.manage.mapper.FloorMapper;
import edu.room.manage.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 */
@Service
public class FloorServiceImpl extends BaseServiceImpl<FloorMapper, Floor> implements FloorService {

    @Autowired
    private FloorMapper floorMapper;

}
