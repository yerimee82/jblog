package com.poscodx.jblog.security;

import com.poscodx.jblog.vo.UserVo;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. handler 종류 확인
        if(!(handler instanceof HandlerMethod)) {
            // DefaultServletHandler가 처리하는 경우(정적자원, /assets/**, mapping이 안되어 있는 URL)
            return true;
        }

        //2. casting
        HandlerMethod handlerMethod = (HandlerMethod)handler;

        //3. Handler Method의 @Auth 가져오기
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

        //4. Handler Method에 @Auth가 없으면 Type(Class)에 붙어 있는지 확인
        if(auth == null) {
            auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
        }

        //5. Type나 Method에 @Auth가 없는 경우
        if(auth == null) {
            return true;
        }

        //6. @Auth가 붙어있기 때문에 인증(Authentication) 여부 확인
        HttpSession session = request.getSession();
        UserVo authUser = (UserVo)session.getAttribute("authUser");

        if(authUser == null) {
            response.sendRedirect(request.getContextPath()+"/user/login");
            return false;
        }

        //7. 현재 요청된 블로그의 소유자 확인 (URL에서 사용자 아이디 추출)
        String requestURI = request.getRequestURI();
        String[] uriParts = requestURI.split("/");

        for (int i = 0; i < uriParts.length; i++) {
            System.out.println(i+":"+uriParts[i]);
        }

        if (!isBlogOwner(uriParts, authUser.getId())) {
            response.sendRedirect(request.getContextPath());
            return false;
        }

        //10. 블로그 소유자가 맞다면 접근 허용
        return true;
    }
    private boolean isBlogOwner(String[] uriParts, String userId) {
        return userId.equals(uriParts[2]);
    }
}
