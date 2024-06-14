package com.poscodx.jblog.security;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        UserVo authUser = userService.getUser(id, password);
        if(authUser == null) {
            request.setAttribute("id", id);
            request.setAttribute("result", "fail");
            request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request,response);
            return false;
        }

        /* login 처리 */
        HttpSession session = request.getSession(true);
        session.setAttribute("authUser", authUser);
        // 성공 시, 메인 페이지로 리디렉션
        response.sendRedirect(request.getContextPath() + "/" + authUser.getId());

        return false;
    }
}
