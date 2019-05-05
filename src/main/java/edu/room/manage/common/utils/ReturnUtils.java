package edu.room.manage.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

/**
 * JSON统一返回数据格式
 *
 * @author 执笔
 */
public class ReturnUtils {

    /**
     * 操作成功
     *
     * @param msg
     * @param obj
     * @param referer
     * @return
     */
    public static ModelMap success(String msg, Object obj, String referer) {
        msg = StringUtils.isEmpty(msg) || StringUtils.isBlank(msg) ? "操作成功" : msg;
        return fullMap(1, "success", msg, referer, obj);
    }

    /**
     * 操作成功
     *
     * @param msg
     * @param obj
     * @return
     */
    public static ModelMap success(String msg, Object obj) {
        msg = StringUtils.isEmpty(msg) || StringUtils.isBlank(msg) ? "操作成功" : msg;
        return fullMap(1, "success", msg, null, obj);
    }

    /**
     * 操作成功
     *
     * @param msg
     * @return
     */
    public static ModelMap success(String msg) {
        msg = StringUtils.isEmpty(msg) || StringUtils.isBlank(msg) ? "操作成功" : msg;
        return fullMap(1, "success", msg, null, null);
    }

    /**
     * 操作失败
     *
     * @param msg
     * @param obj
     * @param referer
     * @return
     */
    public static ModelMap error(String msg, Object obj, String referer) {
        msg = StringUtils.isEmpty(msg) || StringUtils.isBlank(msg) ? "操作失败" : msg;
        return fullMap(0, "error", msg, referer, obj);
    }

    /**
     * 操作失败
     *
     * @param msg
     * @param obj
     * @return
     */
    public static ModelMap error(String msg, Object obj) {
        msg = StringUtils.isEmpty(msg) || StringUtils.isBlank(msg) ? "操作失败" : msg;
        return fullMap(0, "error", msg, null, obj);
    }

    /**
     * 操作失败
     *
     * @param msg
     * @return
     */
    public static ModelMap error(String msg) {
        msg = StringUtils.isEmpty(msg) || StringUtils.isBlank(msg) ? "操作失败" : msg;
        return fullMap(0, "error", msg, null, null);
    }


    /**
     * 填充返回数据
     *
     * @param status
     * @param state
     * @param msg
     * @param referer
     * @param result
     * @return
     */
    private static ModelMap fullMap(Integer status, String state, String msg, String referer, Object result) {
        ModelMap mp = new ModelMap();
        mp.put("status", status);
        mp.put("state", state);
        mp.put("msg", msg);
        mp.put("referer", referer);
        mp.put("result", result);
        return mp;
    }

}
