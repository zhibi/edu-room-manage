package edu.hrm.common.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 执行sql的时候自动填充时间
 *
 * @author 执笔
 * @date 2019/4/12 9:38
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoTime {

    /**
     * 插入时是否更新
     *
     * @return
     */
    boolean insert() default false;

    /**
     * 更新时是否更新
     *
     * @return
     */
    boolean update() default true;
}
