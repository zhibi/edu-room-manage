package edu.room.manage.service.impl;

import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Menu;
import edu.room.manage.mapper.MenuMapper;
import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getChildMenuList(ArrayList<Menu> menuList, Integer parentId) {
        MybatisCondition condition = new MybatisCondition()
                .eq("parent_id", parentId)
                .order("sort", false);
        List<Menu> list = menuMapper.selectByExample(condition);
        for (Menu menu : list) {
            menuList.add(menu);
            getChildMenuList(menuList, menu.getId());
        }
        return menuList;
    }

}
