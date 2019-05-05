package edu.hrm.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON 的工具类
 *
 * @author 执笔
 */
@Slf4j
public class JSONUtils {

    /**
     * 将json转成map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(String json) {
        return toMap(json, new ObjectMapper());
    }

    /**
     * 将json转换成map
     *
     * @param objectMapper
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(String json, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            log.warn("→ JSON 解析异常：{}  {}", json, e.getMessage());
            return new HashMap<>(0);
        }
    }

    /**
     * 对象转换成map
     *
     * @param object
     * @return
     */
    public static Map<String, Object> toMap(Object object, ObjectMapper objectMapper) {
        if (object == null) {
            return new HashMap<>(0);
        }
        String json = toJson(object, objectMapper);
        return toMap(json, objectMapper);
    }

    /**
     * 对象转成map
     *
     * @param object
     * @return
     */
    public static Map<String, Object> toMap(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (object == null) {
            return new HashMap<>(0);
        }
        String json = toJson(object, objectMapper);
        return toMap(json, objectMapper);
    }

    /**
     * 将对象转换为json
     *
     * @param obj
     * @return
     * @author 执笔
     * @Date 2016年10月26日下午3:17:11
     */
    public static String toJson(Object obj, ObjectMapper objectMapper) {
        if (null == obj) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("→ JSON 转换异常：{}  {}", obj, e.getMessage());
            return null;
        }
    }

    /**
     * 将对象转换为json
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return toJson(obj, new ObjectMapper());
    }

    /**
     * 将json转换为object
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObj(String json, Class<T> clazz, ObjectMapper objectMapper) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.warn("→ JSON 解析异常：{}  {}", json, e.getMessage());
            return null;
        }
    }

    /**
     * 将json转换为object
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObj(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        //不对应字段的时候
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return toObj(json, clazz, objectMapper);
    }

    /**
     * map 转换成对象
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObj(Map map, Class<T> clazz, ObjectMapper objectMapper) {
        if (null == map) {
            return null;
        }
        String json = toJson(map, objectMapper);
        return toObj(json, clazz, objectMapper);
    }

    /**
     * map 转换成对象
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObj(Map map, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (null == map) {
            return null;
        }
        String json = toJson(map, objectMapper);
        return toObj(json, clazz, objectMapper);
    }

    /**
     * 将json串中的某个属性转换成对象
     *
     * @param json
     * @param key
     * @param clazz
     * @param objectMapper
     * @param <T>
     * @return
     */
    public static <T> T readObj(String json, String key, Class<T> clazz, ObjectMapper objectMapper) {
        Map<String, Object> objectMap = toMap(json, objectMapper);
        Object              obj       = objectMap.get(key);
        if (null != obj) {
            return toObj(toJson(obj, objectMapper), clazz, objectMapper);
        }
        return null;
    }

    /**
     * 将json串中的某个属性转换成对象
     * @param json
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T readObj(String json, String key, Class<T> clazz) {
        return readObj(json, key, clazz, new ObjectMapper());
    }

}
