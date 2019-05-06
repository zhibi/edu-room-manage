package edu.room.manage.dto;

import edu.room.manage.domain.Classes;
import lombok.Data;

/**
 * @author 执笔
 * @date 2019/5/6 9:42
 */
@Data
public class ClassesDTO extends Classes {

    /**
     * 辅导员名字
     */
    private String userName;
}
