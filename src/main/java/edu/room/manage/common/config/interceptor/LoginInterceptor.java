package edu.eat.order.config.interceptor;

import edu.eat.order.base.annocation.RequestLogin;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 执笔
 * @date 2019/4/2 19:17
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final String SESSION_USER = "sessionUser";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object attribute = request.getSession().getAttribute(SESSION_USER);
        String contextPath = request.getContextPath();
        boolean flag = false;
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            flag = method.getBeanType().isAnnotationPresent(RequestLogin.class);
            if (!flag) {
                flag = method.hasMethodAnnotation(RequestLogin.class);
            }
        }
        if (flag && null == attribute) {
            response.sendRedirect(contextPath + "/login");
            return false;
        }
        return true;
    }
}
