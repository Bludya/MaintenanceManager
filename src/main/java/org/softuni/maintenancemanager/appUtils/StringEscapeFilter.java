package org.softuni.maintenancemanager.appUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class StringEscapeFilter extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.getParameterMap().forEach((key, values) -> {
            for (int i = 0; i < values.length; i++) {
                values[i] = CharacterEscapes.escapeString(values[i]);
            }
        });
        return super.preHandle(request, response, handler);
    }
}
