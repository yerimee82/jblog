package com.poscodx.jblog.interceptor;

import com.poscodx.jblog.security.AuthUser;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class BlogInterceptor implements HandlerInterceptor {
    private final BlogService blogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = getUserId(request);
        if (userId == null) {
            return true;  // 유효한 사용자 아이디가 없으면 그냥 통과
        }

        BlogVo blogVo = blogService.getBlog(userId);
        if (blogVo != null) {
            request.getServletContext().setAttribute("blog", blogVo);
        }

        return true;
    }

    private String getUserId(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String[] uriParts = requestURI.split("/");
        if (uriParts.length < 2) {
            return null;
        }
        return uriParts[2];
    }
}
