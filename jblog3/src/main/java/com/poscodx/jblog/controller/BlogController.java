package com.poscodx.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController {
    @RequestMapping({"/{id}", "/{id}/{categoryNo}","/{id}/{categoryNo}/{postNo}"})
    public String index(
            @PathVariable("id") String id,
            @PathVariable("categoryNo") Long categoryNo,
            @PathVariable("postNo") Long postNo
    ) {
        return "blog/main";
    }
}
