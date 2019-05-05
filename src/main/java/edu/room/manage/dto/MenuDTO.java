package edu.room.manage.dto;

import edu.room.manage.domain.Menu;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author 执笔
 * @date 2019/5/1 22:15
 */
@Data
public class MenuDTO extends Menu {

    /**
     * 子菜单
     */
    List<MenuDTO> children;

    public MenuDTO(Menu menu) {
        BeanUtils.copyProperties(menu,this);
    }
}
