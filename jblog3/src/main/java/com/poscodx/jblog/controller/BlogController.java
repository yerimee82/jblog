package com.poscodx.jblog.controller;

import com.poscodx.jblog.exception.BlogNotFoundException;
import com.poscodx.jblog.security.Auth;
import com.poscodx.jblog.security.AuthUser;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            @PathVariable(value = "categoryNo") Optional<Long> categoryNo,
            @PathVariable(value = "postNo") Optional<Long> postNo,
            Model model
    ) {

        UserVo userVo = userService.getUser(id);
        if (userVo == null) {
            throw new BlogNotFoundException("해당 블로그를 찾을 수 없습니다." + id);
        }

        List<CategoryVo> categories = blogService.getCategories(id);
        model.addAttribute("categories", categories);

        List<PostVo> posts = fetchPosts(id, categoryNo, postNo);
        model.addAttribute("posts", posts);

        return "blog/main";
    }

    private List<PostVo> fetchPosts(String id, Optional<Long> categoryNo, Optional<Long> postNo) {
        if (postNo.isPresent()) {
            return blogService.getSelectedPost(postNo.get());
        }

        if (categoryNo.isPresent()) {
            return blogService.getPosts(id, categoryNo.get());
        }

        return blogService.getPosts(id);
    }


    @Auth
    @RequestMapping("/admin/basic" )
    public String adminBasic(@PathVariable("id") String id, Model model) {
        BlogVo blog = blogService.getBlog(id);
        model.addAttribute("blog", blog);

        return "blog/admin-basic";
    }

    @Auth
    @RequestMapping(value="/admin/basic/update" ,method= RequestMethod.POST)
    public String updateAdminBasic(@PathVariable("id") String id,
                                   @ModelAttribute BlogVo blogVo, MultipartFile file) {
        String logo = fileUploadService.restore(file);
        if(logo != null) {
            blogVo.setLogo(logo);
        }

        System.out.println(blogVo);
        blogService.updateBlog(blogVo);
        servletContext.setAttribute("blog", blogVo);
        return "redirect:/" + id + "/admin/basic";
    }

    @Auth
    @RequestMapping("/admin/category")
    public String adminCategory(@PathVariable("id") String id, Model model) {
        List<CategoryVo> categories = blogService.getCategories(id);
        model.addAttribute("list", categories);

        return "blog/admin-category";
    }

    @Auth
    @PostMapping("/admin/category/add")
    public String addCategory(@PathVariable("id") String id,
                              @RequestParam("name") String name,
                              @RequestParam("desc") String description) {
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setName(name);
        categoryVo.setDescription(description);
        categoryVo.setId(id);

        blogService.addCategory(categoryVo);
        return "redirect:/" + id + "/admin/category";
    }

    @Auth
    @PostMapping("/admin/category/delete/{no}")
    public String deleteCategory(@PathVariable("id") String id,
                                 @PathVariable("no") Long categoryNo) {
        blogService.deleteCategory(categoryNo, id);
        return "redirect:/" + id + "/admin/category";
    }

    @Auth
    @RequestMapping("/admin/write")
    public String adminWrite(@PathVariable("id") String id, Model model) {
        List<CategoryVo> categories = blogService.getCategories(id);
        model.addAttribute("categories", categories);
//        if(!id.equals(authUser.getId())) {
//
//        }
        return "blog/admin-write";
    }


    @Auth
    @PostMapping ("/admin/write")
    public String addPost(@PathVariable("id") String id,
                          @RequestParam("title") String title,
                          @RequestParam("content") String content,
                          @RequestParam(value = "category", required = false) Long categoryNo) {
        PostVo postVo = new PostVo();
        postVo.setTitle(title);
        postVo.setContents(content);
        postVo.setCategoryNo(categoryNo);

        blogService.addPost(postVo);
        return "redirect:/" + id;
    }
}
