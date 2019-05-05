package edu.room.manage.dto;

import edu.room.manage.domain.Menu;
import edu.room.manage.domain.RoleMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单树
 *
 * @author 执笔
 */
public class MenuTree {

    private List<Menu> nodes;

    private List<RoleMenu> checkNodes;


    /**
     * 创建一个新的实例 Tree.
     *
     * @param nodes 将树的所有节点都初始化进来。
     */
    public MenuTree(List<Menu> nodes, List<RoleMenu> checkNodes) {
        this.nodes = nodes;
        this.checkNodes = checkNodes;
    }


    /**
     * buildTree
     * 描述:  创建树
     *
     * @return List<Map < String, Object>>
     * @throws
     */
    public List<Map<String, Object>> buildTree() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Menu node : nodes) {
            //这里判断父节点，需要自己更改判断
            if (0 == node.getParentId()) {
                Map<String, Object> map = buildTreeChildsMap(node);
                list.add(map);
            }
        }
        return list;
    }


    /**
     * buildChilds
     * 描述:  创建树下的节点。
     *
     * @param node
     * @return List<Map < String, Object>>
     * @throws
     */
    private List<Map<String, Object>> buildTreeChilds(Menu node) {
        List<Map<String, Object>> list       = new ArrayList<Map<String, Object>>();
        List<Menu>                childNodes = getChilds(node);
        for (Menu childNode : childNodes) {
            Map<String, Object> map = buildTreeChildsMap(childNode);
            list.add(map);
        }
        return list;
    }

    /**
     * buildChildMap
     * 描述:生成Map节点
     *
     * @param childNode
     * @return Map<String, Object>
     */
    private Map<String, Object> buildTreeChildsMap(Menu childNode) {
        Map<String, Object> map      = new HashMap<>(5);
        Map<String, Object> stateMap = new HashMap<>(4);
        // 是否选中
        stateMap.put("checked", false);
        for (RoleMenu checkNode : checkNodes) {
            if (checkNode.getMenuId().equals(childNode.getId())) {
                stateMap.put("checked", true);
                break;
            }
        }
        // 是否不可用
        stateMap.put("disabled", false);
        stateMap.put("expanded", false);
        stateMap.put("selected", false);
        map.put("id", childNode.getId());
        map.put("text", childNode.getName());
        map.put("url", childNode.getUrl());
        map.put("state", stateMap);
        List<Map<String, Object>> childs = buildTreeChilds(childNode);
        if (childs.size() > 0) {
            map.put("nodes", childs);
        }
        return map;
    }


    /**
     * getChilds
     * 描述:  获取子节点
     *
     * @param parentNode
     * @return List<Resource>
     * @throws
     */
    private List<Menu> getChilds(Menu parentNode) {
        List<Menu> childNodes = new ArrayList<Menu>();
        for (Menu node : nodes) {
            if (node.getParentId().equals(parentNode.getId())) {
                childNodes.add(node);
            }
        }
        return childNodes;
    }

    /**
     * buildTree
     * 描述:  创建菜单树
     */
    public List<MenuDTO> buildTreeGrid() {
        List<MenuDTO> list = new ArrayList<MenuDTO>();
        for (Menu node : nodes) {
            //这里判断父节点，需要自己更改判断
            if (node.getParentId() == 0) {
                MenuDTO       menuDTO = new MenuDTO(node);
                List<MenuDTO> children  = buildTreeGridChilds(node);
                menuDTO.setChildren(children);
                list.add(menuDTO);
            }
        }
        return list;
    }

    /**
     * buildChilds
     * 描述:  创建树下的节点。
     *
     * @param node
     * @return List<Map < String, Object>>
     */
    private List<MenuDTO> buildTreeGridChilds(Menu node) {
        List<MenuDTO> list       = new ArrayList<MenuDTO>();
        List<Menu>    childNodes = getChilds(node);
        for (Menu childNode : childNodes) {
            MenuDTO       menuDTO  = new MenuDTO(childNode);
            List<MenuDTO> children = buildTreeGridChilds(childNode);
            menuDTO.setChildren(children);
            list.add(menuDTO);
        }
        return list;
    }


}
