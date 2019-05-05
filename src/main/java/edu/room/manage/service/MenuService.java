package edu.room.manage.service;

import edu.room.manage.common.base.service.BaseService;
import edu.room.manage.domain.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 */

public interface MenuService extends BaseService<Menu> {


    /**
     *
     * @param menuList
     * @param parentId
     * @return
     */
    List<Menu> getChildMenuList(ArrayList<Menu> menuList, Integer parentId);
}
