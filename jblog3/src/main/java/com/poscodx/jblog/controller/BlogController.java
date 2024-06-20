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
import java.util.*;

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

        Map<String, Object> result = fetchPosts(id, categoryNo, postNo);
        model.addAttribute("posts", result.get("posts"));
        model.addAttribute("selectedPost", result.get("selectedPost"));

        return "blog/main";
    }

    private Map<String, Object> fetchPosts(String id, Optional<Long> categoryNo, Optional<Long> postNo) {
        Map<String, Object> result = new HashMap<>();


        if (postNo.isPresent()) {
            List<PostVo> posts = blogService.getPosts(id);
            result.put("posts", posts);
            result.put("selectedPost", blogService.getSelectedPost(postNo.get()).get(0));
        } else if (categoryNo.isPresent()) {
            List<PostVo> posts = blogService.getPosts(id, categoryNo.get());
            result.put("posts", posts);
        } else {  // (categoryNo.isEmpty() && postNo.isEmpty())
            result.put("posts", blogService.getPosts(id));
        }

        return result;
    }


    @Auth
    @RequestMapping("/admin/basic" )
    public String adminBasic(@AuthUser UserVo authUser, @PathVariable("id") String id, Model model) {
        BlogVo blog = blogService.getBlog(authUser.getId());
        model.addAttribute("blog", blog);

        return "blog/admin-basic";
    }

    @Auth
    @RequestMapping(value="/admin/basic/update" ,method= RequestMethod.POST)
    public String updateAdminBasic(@AuthUser UserVo authUser,
                                   @PathVariable("id") String id,
                                   @ModelAttribute BlogVo blogVo, MultipartFile file) {
        String logo = fileUploadService.restore(file);
        if(logo != null) {
            blogVo.setLogo(logo);
        }

        System.out.println(blogVo);
        blogService.updateBlog(blogVo);
        servletContext.setAttribute("blog", blogVo);
        return "redirect:/" + authUser.getId() + "/admin/basic";
    }

    @Auth
    @RequestMapping("/admin/category")
    public String adminCategory(@AuthUser UserVo authUser,
            @PathVariable("id") String id, Model model) {
        List<CategoryVo> categories = blogService.getCategories(authUser.getId());
        model.addAttribute("list", categories);

        return "blog/admin-category";
    }

    @Auth
    @PostMapping("/admin/category/add")
    public String addCategory(@AuthUser UserVo authUser,
                              @PathVariable("id") String id,
                              @RequestParam("name") String name,
                              @RequestParam("desc") String description) {
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setName(name);
        categoryVo.setDescription(description);
        categoryVo.setId(authUser.getId());

        blogService.addCategory(categoryVo);
        return "redirect:/" + authUser.getId() + "/admin/category";
    }

    @Auth
    @PostMapping("/admin/category/delete/{no}")
    public String deleteCategory(@AuthUser UserVo authUser,
                                 @PathVariable("id") String id,
                                 @PathVariable("no") Long categoryNo) {
        blogService.deleteCategory(categoryNo, authUser.getId());
        return "redirect:/" + authUser.getId() + "/admin/category";
    }

    @Auth
    @RequestMapping("/admin/write")
    public String adminWrite(@AuthUser UserVo authUser,@PathVariable("id") String id, Model model) {
        List<CategoryVo> categories = blogService.getCategories(authUser.getId());
        model.addAttribute("categories", categories);
        return "blog/admin-write";
    }


    @Auth
    @PostMapping ("/admin/write")
    public String addPost(@AuthUser UserVo authUser,
                          @PathVariable("id") String id,
                          @RequestParam("title") String title,
                          @RequestParam("content") String content,
                          @RequestParam(value = "category", required = false) Long categoryNo) {
        PostVo postVo = new PostVo();
        postVo.setTitle(title);
        postVo.setContents(content);
        postVo.setCategoryNo(categoryNo);

        blogService.addPost(postVo);
        return "redirect:/" + authUser.getId();
    }
}
