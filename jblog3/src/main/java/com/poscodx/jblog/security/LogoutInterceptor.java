package com.poscodx.jblog.security;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if(session != null) {
            session.removeAttribute("authUser");
            session.invalidate();
        }

//        request.getServletContext().removeAttribute("blog");

        String redirectPath = determineRedirectPath(request);
        response.sendRedirect(redirectPath);

        return false;
    }

    private String determineRedirectPath(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String[] uriParts = requestURI.split("/");

        if (isUserBlogLogoutRequest(uriParts)) {
            String userId = uriParts[2];
            return contextPath + "/" + userId;
        }

        return contextPath;
    }

    private boolean isUserBlogLogoutRequest(String[] uriParts) {
        return uriParts.length > 4;
    }
}
