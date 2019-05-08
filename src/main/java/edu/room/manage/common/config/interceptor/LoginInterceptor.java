package edu.room.manage.common.config.interceptor;

import edu.room.manage.common.annotation.Operation;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static edu.room.manage.common.context.Constant.SESSION_USER;

/**
 * 判断是否需要登录
 *
 * @author 执笔
 * @date 2019/4/2 19:17
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object    attribute = request.getSession().getAttribute(SESSION_USER);
        Operation operation = null;
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            if (method.hasMethodAnnotation(Operation.class)) {
                operation = method.getMethodAnnotation(Operation.class);
            } else {
                if (method.getBeanType().isAnnotationPresent(Operation.class)) {
                    operation = method.getBeanType().getAnnotation(Operation.class);
                }
            }
        }
        if (operation != null && operation.needLogin() && null == attribute) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
