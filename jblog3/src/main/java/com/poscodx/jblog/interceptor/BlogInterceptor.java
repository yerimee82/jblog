package com.poscodx.jblog.interceptor;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.vo.BlogVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class BlogInterceptor implements HandlerInterceptor {
    private final BlogService blogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        BlogVo blogVo = (BlogVo) request.getServletContext().getAttribute("blog");
        if(blogVo == null) {
            String id = getUserId(request);
            blogVo = blogService.getBlog(id);
            System.out.println(blogVo);
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
