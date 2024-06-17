package com.poscodx.jblog.service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogVo getBlog(String id) {
        return blogRepository.find(id);
    }

    public void updateBlog(BlogVo vo) {
        blogRepository.update(vo);
    }

    public void addCategory(CategoryVo vo) {
        blogRepository.insertCategory(vo);
    }

    public List<CategoryVo> getCategories(String id) {
        return blogRepository.findAllCategories(id);
    }

    public void deleteCategory(Long no) {
        blogRepository.deleteCategory(no);
    }

}
