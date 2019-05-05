package edu.eat.order.config.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 参数回显拦截器
 *
 * @author 执笔
 */
@Component
public class ParamInterceptor extends HandlerInterceptorAdapter {

    private List<String> ignoreParams = new ArrayList<String>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement();
            if (!ignoreParams.contains(string)) {
                request.setAttribute(string, request.getParameter(string));
            }
        }
        return super.preHandle(request, response, handler);
    }

    public List<String> getIgnoreParams() {
        return ignoreParams;
    }

    public void setIgnoreParams(List<String> ignoreParams) {
        this.ignoreParams = ignoreParams;
    }
}
