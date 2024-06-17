package com.poscodx.jblog.controller;

import com.poscodx.jblog.exception.BlogNotFoundException;
import com.poscodx.jblog.security.Auth;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("/{id:(?!assets).*}")
@RequiredArgsConstructor
public class BlogController {
    private final UserService userService;
    private final BlogService blogService;
    private final FileUploadService fileUploadService;
    private final ServletContext servletContext;

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
    public String adminBasic(@PathVariable("id") String id, Model model) {
        BlogVo blog = blogService.getBlog(id);
        model.addAttribute("blog", blog);

        return "blog/admin-basic";
    }

    @Auth
    @PostMapping("/admin/basic-update")
    public String updateAdminBasic(@ModelAttribute BlogVo blogVo, MultipartFile file) {
        String logo = fileUploadService.restore(file);
        if(logo != null) {
            blogVo.setLogo(logo);
        }

        blogService.updateBlog(blogVo);
        servletContext.setAttribute("blog", blogVo);
        return "redirect:/admin/basic";
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
