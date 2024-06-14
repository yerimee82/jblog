package com.poscodx.jblog.exception;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@Controller
public class GlobalExceptionHandler {
    private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public String handler(Exception e, Model model) {
        // 1. 로깅(logging)
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        logger.error(errors.toString());

        // 2. 사과(종료)
        model.addAttribute("error", errors.toString());
        return "errors/exception";

    }

    @ExceptionHandler(BlogNotFoundException.class)
    public String handleBlogNotFoundException(BlogNotFoundException e, HttpServletRequest request, Model model) {
        logger.error("Request: " + request.getRequestURL() + " raised " + e);

        model.addAttribute("message", e.getMessage());
        return "errors/500";
    }

}
