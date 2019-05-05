package edu.room.manage.service.impl;

import edu.room.manage.common.base.service.BaseServiceImpl;
import edu.room.manage.common.mybatis.condition.MybatisCondition;
import edu.room.manage.domain.Role;
import edu.room.manage.mapper.RoleMapper;
import edu.room.manage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllEnable() {
        MybatisCondition example = new MybatisCondition()
                .eq("enable", 1);
        return roleMapper.selectByExample(example);
    }

    @Override
    public List<Role> getChildRoleList(ArrayList<Role> roleList, Integer parentId) {
        MybatisCondition condition = new MybatisCondition()
                .eq("parent_id", parentId)
                .order("sort", false);
        List<Role> list = roleMapper.selectByExample(condition);
        for (Role role : list) {
            roleList.add(role);
            getChildRoleList(roleList, role.getId());
        }
        return roleList;
    }
}
