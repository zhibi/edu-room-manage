package edu.room.manage.common.mybatis.condition;

import edu.room.manage.common.base.dto.BaseDomain;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 * 组装mybatis请求条件
 */
@Getter
public class MybatisCondition extends BaseDomain {

    /**
     * 是否排除重复的
     */
    private boolean distinct;

    /**
     * 排序语句
     */
    private String orderByClause = null;

    /**
     * 多组查询条件
     */
    private List<Criteria> oredCriteria;

    /**
     * 一组查询条件
     */
    private Criteria criteria;

    public MybatisCondition() {
        oredCriteria = new ArrayList<>();
        criteria = new Criteria();
        oredCriteria.add(criteria);
    }


    /**
     * 添加  or 语句
     *
     * @param wrapper
     * @return
     */
    public MybatisCondition or(MybatisCondition wrapper) {
        oredCriteria.addAll(wrapper.getOredCriteria());
        return this;
    }

    /**
     * column = value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisCondition eq(String column, Object value) {
        criteria.addCriterion(column + " =", value);
        return this;
    }

    /**
     * column <> value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisCondition eqNot(String column, Object value) {
        criteria.addCriterion(column + " <>", value);
        return this;
    }

    /**
     * column between value1,value2
     *
     * @param column
     * @param value1
     * @param value2
     * @return
     */
    public MybatisCondition between(String column, Object value1, Object value2) {
        criteria.addCriterion(column + " BETWEEN ", value1, value2);
        return this;
    }

    /**
     * column > value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisCondition gt(String column, Object value) {
        criteria.addCriterion(column + " >", value);
        return this;
    }

    /**
     * column >= value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisCondition gte(String column, Object value) {
        criteria.addCriterion(column + " >=", value);
        return this;
    }

    /**
     * column < value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisCondition lt(String column, Object value) {
        criteria.addCriterion(column + " <", value);
        return this;
    }

    /**
     * column <= value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisCondition lte(String column, Object value) {
        criteria.addCriterion(column + " <=", value);
        return this;
    }

    /**
     * column is null
     *
     * @param column
     * @return
     */
    public MybatisCondition isNull(String column) {
        criteria.addCriterion(column + " IS NULL");
        return this;
    }

    /**
     * column is not null
     *
     * @param column
     * @return
     */
    public MybatisCondition isNotNull(String column) {
        criteria.addCriterion(column + " IS NOT NULL");
        return this;
    }

    /**
     * column in (1,2,3)
     *
     * @param column
     * @param values
     * @return
     */
    public MybatisCondition in(String column, List<Object> values) {
        if (values == null || values.size() == 0) {
            return this;
        }
        criteria.addCriterion(column + " IN", values);
        return this;
    }


    /**
     * column not in (1,2,3)
     *
     * @param column
     * @param values
     * @return
     */
    public MybatisCondition notIn(String column, List<Object> values) {
        if (values == null || values.size() == 0) return this;
        criteria.addCriterion(column + " NOT IN", values);
        return this;
    }

    /**
     * column like '%value%'
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisCondition like(String column, String value) {
        if (value == null) {
            return this;
        }
        criteria.addCriterion(column + " LIKE '%" + value + "%'");
        return this;
    }

    /**
     * 自定义条件
     *
     * @param condition
     * @return
     */
    public MybatisCondition condition(String condition) {
        criteria.addCriterion(condition);
        return this;
    }


    /**
     * 添加排序
     *
     * @param order
     * @param asc   是否顺序
     */
    public MybatisCondition order(String order, boolean asc) {
        orderByClause = orderByClause == null ? "" : orderByClause + " ,";
        orderByClause = orderByClause + " " + order + " " + (asc ? "ASC" : "DESC");
        return this;
    }

    /**
     * 添加排序
     *
     * @param order
     */
    public MybatisCondition order(String order) {
        return order(order, true);
    }

    /**
     * 分页
     *
     * @return
     */
    public MybatisCondition page(BaseDomain dto) {
        this.setLimit(dto.getLimit());
        this.setOffset(dto.getOffset());
        return this;
    }

    /**
     * 分页
     *
     * @return
     */
    public MybatisCondition page(Integer limit, Integer offset) {
        this.setLimit(limit);
        this.setOffset(offset);
        return this;
    }
}