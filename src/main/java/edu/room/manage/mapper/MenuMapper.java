package edu.room.manage.mapper;

import edu.room.manage.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import edu.room.manage.common.CustomerMapper;

import java.util.List;
import java.util.Set;

/**
 * @author 执笔
 */
@Mapper
public interface MenuMapper extends CustomerMapper<Menu> {

    /**
     * 获取某一个用户的菜单编码
     *
     * @param userId
     * @return
     */
    Set<String> selectMenuCodeByUserId(Integer userId);

    /**
     * 获取所有菜单编码
     *
     * @return
     */
    Set<String> selectALLMenuCode();

    /**
     * 根据用户ID获取菜单
     *
     * @param userId
     * @return
     */
    List<Menu> selectMenuByUserId(Integer userId);

    /**
     * 获取所有菜单
     *
     * @return
     */
    List<Menu> selectAllMenu();

    /**
     * 根据角色获取菜单
     *
     * @param roleId
     * @return
     */
    List<Menu> selectMenuByRoleId(Integer roleId);

    /**
     * 删除菜单
     * @param id
     */
    void deleteById(Integer id);
}