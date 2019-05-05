package edu.room.manage.common.mybatis.condition;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 一组查询条件
 *
 * @author 执笔
 * @date 2018/12/5 10:36
 */
public class Criteria {
    @Getter
    private List<Criterion> criteria;

    Criteria() {
        super();
        criteria = new ArrayList<>();
    }

    public boolean isValid() {
        return criteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
        return criteria;
    }

    void addCriterion(String condition) {
        if (StringUtils.isEmpty(condition)) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        criteria.add(new Criterion(condition));
    }

    void addCriterion(String condition, Object value) {
        if (value == null) {
            return;
        }
        criteria.add(new Criterion(condition, value));
    }

    void addCriterion(String condition, Object value1, Object value2) {
        if (value1 == null || value2 == null) {
            return;
        }
        criteria.add(new Criterion(condition, value1, value2));
    }
}