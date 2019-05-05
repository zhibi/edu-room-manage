package edu.room.manage.service;

import edu.room.manage.common.base.service.BaseService;
import edu.room.manage.domain.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 所有可用角色
     *
     * @return
     */
    List<Role> getAllEnable();

    /**
     * 查看下级职位
     *
     * @param roleList
     * @param parentId
     * @return
     */
    List<Role> getChildRoleList(ArrayList<Role> roleList, Integer parentId);
}
