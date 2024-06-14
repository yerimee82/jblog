package com.poscodx.jblog.controller.api;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("userApiController")
@RequestMapping("/user/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/checkId")
    public Object checkId(
            @RequestParam(value = "id", defaultValue = "") String id
    ) {
        UserVo userVo = userService.getUser(id);
        return Map.of("exist", userVo != null);
    }
}
