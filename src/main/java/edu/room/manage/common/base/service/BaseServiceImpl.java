package edu.hrm.common.base.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hrm.common.base.dto.BaseDomain;
import edu.hrm.common.mybatis.condition.MybatisCondition;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

/**
 * 基础service 实现
 *
 * @param <T>
 * @author 执笔
 */
public abstract class BaseServiceImpl<E extends Mapper<T>, T extends BaseDomain> implements BaseService<T> {

    @Autowired
    protected E mapper;


    @Override
    public PageInfo<T> selectPage(T dto) {
        PageHelper.orderBy("id desc");
        startPage(dto);
        return new PageInfo<>(mapper.select(dto));
    }

    @Override
    public PageInfo<T> selectPage(MybatisCondition condition) {
        startPage(condition);
        return new PageInfo<>(mapper.selectByExample(condition));
    }

    @Override
    public int merge(T dto) {
        if (dto.getId() == null) {
            return mapper.insertSelective(dto);
        } else {
            return mapper.updateByPrimaryKeySelective(dto);
        }
    }

    @Override
    public boolean isExist(MybatisCondition condition) {
        int count = mapper.selectCountByExample(condition);
        return count != 0;
    }


    /**
     * 开始分页
     *
     * @param dto
     */
    protected void startPage(BaseDomain dto) {
        PageHelper.offsetPage(dto.getOffset(), dto.getLimit());
    }
}
