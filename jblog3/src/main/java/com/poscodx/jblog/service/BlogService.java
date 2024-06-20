package com.poscodx.jblog.service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogVo getBlog(String id) {
        return blogRepository.findBlog(id);
    }

    public void updateBlog(BlogVo vo) {
        blogRepository.updateBlog(vo);
    }

    public void addCategory(CategoryVo vo) {
        blogRepository.insertCategory(vo);
    }

    public List<CategoryVo> getCategories(String id) {
        List<CategoryVo> categories = blogRepository.findAllCategories(id);
        for (CategoryVo category : categories) {
            if ("미분류".equals(category.getName())) {
                category.setName("기타");
            }
        }
        return categories;
    }

    public void deleteCategory(Long no, String id) {
        // 업데이트 먼저
        Long defaultNo = blogRepository.findDefaultNo(id);
        blogRepository.updateCategoryToDefault(defaultNo, no);

        blogRepository.deleteCategory(no);
    }

    public void addPost(PostVo vo) {
        blogRepository.insertPost(vo);
    }

    // 해당 유저의 모든 글 찾기
    public List<PostVo> getPosts(String id) {
        return blogRepository.findAllPosts(id);
    }

    // 카테고리에 맞는 글 찾기

    public List<PostVo> getPosts(String id, Long categoryNo) {
        return blogRepository.findByCategories(id, categoryNo);
    }

    // 글 번호로 찾기
    public List<PostVo> getSelectedPost(Long no) {
        return blogRepository.findByPostNo(no);
    }


}
