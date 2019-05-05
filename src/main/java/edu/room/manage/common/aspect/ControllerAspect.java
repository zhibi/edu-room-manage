package edu.room.manage.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * controller切面
 * 打印所有的请求日志
 *
 * @author 执笔
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {


    private static Pattern PATTERN = Pattern.compile("[ \t\n\r]");

    @Autowired
    private HttpServletRequest request;

    /**
     * 拦截所有的Controller
     */
    @Pointcut("execution(public * *..controller..*Controller.*(..))")
    public void controller() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     * @return
     */
    @Around("controller()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (isRequest(method)) {
            log.info("**** 请求：{} ", buildAccessLog(this.request));
        }
        return joinPoint.proceed();
    }

    /**
     * 是否是http请求
     *
     * @param method
     * @return
     */
    private boolean isRequest(Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)
                || method.isAnnotationPresent(PostMapping.class)
                || method.isAnnotationPresent(GetMapping.class)
                || method.isAnnotationPresent(DeleteMapping.class)
                || method.isAnnotationPresent(PutMapping.class)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 请求需要打印的日志
     *
     * @param request
     * @return
     */
    private String buildAccessLog(HttpServletRequest request) {
        StringBuilder accessLog = new StringBuilder();
        build(accessLog, request.getRemoteHost());
        build(accessLog, request.getMethod());
        build(accessLog, buildRequestPath(request));
        build(accessLog, buildRequestParam(request));
        return accessLog.toString();
    }

    private void build(StringBuilder sb, String str) {
        sb.append(" ");
        if (StringUtils.isEmpty(str)) {
            sb.append("-");
        } else if (PATTERN.matcher(str).find()) {
            sb.append("\"");
            sb.append(str.replace("\"", "\"\""));
            sb.append("\"");
        } else {
            sb.append(str);
        }
    }

    /**
     * 请求路径
     *
     * @return
     */
    private String buildRequestPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * 请求参数
     *
     * @param request
     * @return
     */
    private String buildRequestParam(HttpServletRequest request) {
        StringBuilder       buffer      = new StringBuilder("param:{");
        Enumeration<String> enumeration = request.getParameterNames();
        return getRequestString(request, buffer, enumeration);
    }


    /**
     * 请求里面的数据
     *
     * @param request
     * @param buffer
     * @param enumeration
     * @return
     */
    private String getRequestString(HttpServletRequest request, StringBuilder buffer, Enumeration<String> enumeration) {
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            buffer.append(key).append(":").append(request.getParameter(key)).append(",");
        }
        buffer.append("}");
        return buffer.toString();
    }


}
