package com.poscodx.jblog.service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    public void join(UserVo vo) {
        userRepository.insert(vo);
        createDefaultCategory(vo.getId());
    }

    public UserVo getUser(String id) {
        return userRepository.findById(id);
    }

    public UserVo getUser(String id, String password) {
        return userRepository.findByIdAndPassword(id, password);
    }

    private void createDefaultCategory(String userId) {
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setName("미분류");
        categoryVo.setDescription("미지정 시, 기본 카테고리 입니다.");
        categoryVo.setId(userId);

        blogRepository.insertCategory(categoryVo);
    }
}
