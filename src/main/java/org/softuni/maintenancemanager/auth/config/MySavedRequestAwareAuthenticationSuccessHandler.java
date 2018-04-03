package org.softuni.maintenancemanager.auth.config;

import org.softuni.maintenancemanager.auth.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MySavedRequestAwareAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws ServletException, IOException {
        User user = ((User)authentication.getPrincipal());
        String userJson = "{\"username\": \"" + user.getUsername() + "\"} ";
        response.setHeader("readyState", "4");
        response.setContentType("application/json;charset=UTF-8");
        response.setContentLength(userJson.length());
        response.getOutputStream().write(userJson.getBytes());
        response.getOutputStream().flush();
        this.handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}