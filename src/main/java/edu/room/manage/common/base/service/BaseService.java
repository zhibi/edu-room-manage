package edu.room.manage.common.base.service;

import com.github.pagehelper.PageInfo;
import edu.room.manage.common.base.dto.BaseDomain;
import edu.room.manage.common.mybatis.condition.MybatisCondition;

/**
 * 基础service
 *
 * @param <T>
 * @author 执笔
 */
public interface BaseService<T extends BaseDomain> {

    /**
     * 分页
     *
     * @param dto
     * @return
     */
    PageInfo<T> selectPage(T dto);

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    PageInfo<T> selectPage(MybatisCondition condition);

    /**
     * 更新或者保存
     *
     * @param dto
     * @return
     */
    int merge(T dto);

    /**
     * 判断是否存在
     * @param condition
     * @return
     */
    boolean isExist(MybatisCondition condition);

}
