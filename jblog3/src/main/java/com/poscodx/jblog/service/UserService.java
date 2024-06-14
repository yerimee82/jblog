package com.poscodx.jblog.service;

import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void join(UserVo vo) {
        userRepository.insert(vo);
    }

    public UserVo getUser(String id) {
        return userRepository.findById(id);
    }
}
