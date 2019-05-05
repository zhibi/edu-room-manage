package edu.room.manage.common.mybatis.interceptor;

import edu.room.manage.common.mybatis.annotation.AutoTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 自动添加时间
 * 添加的时候和更新的时候
 *
 * @author 执笔
 * @date 2019/4/11 18:21
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Component
public class UpdateDateInterceptor implements Interceptor {

    public UpdateDateInterceptor() {
        log.info("☆初始化☆ [mybatis 更新时间插件]");
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 获取参数
        Object parameter = invocation.getArgs()[1];
        setFieldDateTime(parameter, sqlCommandType);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 设置属性时间
     *
     * @param parameter      参数对象
     * @param sqlCommandType 类型
     */
    private void setFieldDateTime(Object parameter, SqlCommandType sqlCommandType) {
        if (parameter != null) {
            // 获取所有的属性
            List<Field> fields = FieldUtils.getAllFieldsList(parameter.getClass());
            for (Field field : fields) {
                if (!field.isAnnotationPresent(AutoTime.class)) {
                    continue;
                }
                AutoTime autoTime   = field.getAnnotation(AutoTime.class);
                boolean  needInsert = sqlCommandType == SqlCommandType.INSERT && autoTime.insert();
                boolean  needUpdate = sqlCommandType == SqlCommandType.UPDATE && autoTime.update();
                if (needInsert || needUpdate) {
                    setFieldValue(parameter, field);
                }
            }
        }
    }

    /**
     * 设置属性值
     */
    private void setFieldValue(Object obj, Field field) {
        try {
            field.setAccessible(true);
            if (field.get(obj) != null) {
                return;
            }
            Class<?> type = field.getType();
            if (type.equals(Date.class)) {
                field.set(obj, new Date());
            } else if (type.equals(Long.class)) {
                field.set(obj, System.currentTimeMillis());
            } else if (type.equals(Timestamp.class)) {
                field.set(obj, new Timestamp(System.currentTimeMillis()));
            } else if (type.equals(LocalDate.class)) {
                field.set(obj, LocalDate.now());
            } else if (type.equals(LocalDateTime.class)) {
                field.set(obj, LocalDateTime.now());
            } else if (type.equals(Instant.class)) {
                field.set(obj, Instant.now());
            } else if (type.equals(LocalTime.class)) {
                field.set(obj, LocalTime.now());
            } else if (type.equals(String.class)) {
                field.set(obj, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            log.debug("auto set field:{} value:{}", field.getName(), field.get(obj));
        } catch (IllegalAccessException ignored) {
        }
    }
}
