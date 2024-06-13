package com.poscodx.jblog.controller;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/join")
    public String joinForm(@ModelAttribute UserVo vo) {
        return "user/join";
    }
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute UserVo vo, BindingResult result, Model model) {
        if(result.hasErrors()) {
            Map<String, Object> map = result.getModel();
            model.addAllAttributes(map);
            return "user/join";
        }

        userService.join(vo);
        return "redirect:/user/joinsuccess";
    }

    @GetMapping("/joinsuccess")
    public String joinsuccess() {
        return "user/joinsuccess";
    }
}
