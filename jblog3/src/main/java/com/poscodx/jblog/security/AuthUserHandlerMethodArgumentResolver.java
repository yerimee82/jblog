package com.poscodx.jblog.security;

import com.poscodx.jblog.vo.UserVo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);

        // @AuthUser 가 안 붙어 있다면,
        if(authUser == null) {
            return false;
        }

        // 파라미터 타입이 UserVo 가 아니면,
        if(!parameter.getParameterType().equals(UserVo.class)) {
            return false;
        }

        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        if(!supportsParameter(parameter)) {
            return WebArgumentResolver.UNRESOLVED;
        }
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();

        return session.getAttribute("authUser");
    }
}
