package com.poscodx.jblog.controller;

import com.poscodx.jblog.exception.BlogNotFoundException;
import com.poscodx.jblog.security.Auth;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/{id:(?!assets).*}")
@RequiredArgsConstructor
public class BlogController {
    private final UserService userService;
    @RequestMapping({"","/{categoryNo}", "/{categoryNo}/{postNo}" })
    public String index(
            @PathVariable("id") String id,
            @PathVariable(value = "categoryNo", required = false) Long categoryNo,
            @PathVariable(value = "postNo", required = false) Long postNo) {

        UserVo userVo = userService.getUser(id);
        if (userVo == null) {
            throw new BlogNotFoundException("해당 블로그를 찾을 수 없습니다." + id);
        }

        return "blog/main";
    }
    @Auth
    @RequestMapping("/admin/basic")
    public String adminBasic(@PathVariable("id") String id) {
        return "blog/admin-basic";
    }

    @Auth
    @RequestMapping("/admin/category")
    public String adminCategory(@PathVariable("id") String id) {
        return "blog/admin-category";
    }
    @Auth
    @RequestMapping("/admin/write")
    public String adminWrite(@PathVariable("id") String id) {
//        if(!id.equals(authUser.getId())) {
//
//        }
        return "blog/admin-write";
    }
}
