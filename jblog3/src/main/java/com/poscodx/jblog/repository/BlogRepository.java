package com.poscodx.jblog.repository;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BlogRepository {
    private final SqlSession sqlSession;

    public BlogVo findBlog(String id) {
        return sqlSession.selectOne("blog.find-blog", id);
    }

    public void updateBlog(BlogVo vo) {
        sqlSession.update("blog.update-blog", vo);
    }

    public void insertBlog(BlogVo vo) {
        sqlSession.insert("blog.insertDefault", vo);
    }

    public void insertCategory(CategoryVo vo ) {
        sqlSession.insert("category.insert", vo);
    }

    public List<CategoryVo> findAllCategories(String id) {
        return sqlSession.selectList("category.find", id);
    }

    public void deleteCategory(Long no) {
        sqlSession.delete("category.delete", no);
    }

    public void insertPost(PostVo vo) {
        sqlSession.insert("post.insert", vo);
    }

    public List<PostVo> findAllPosts(String id) {
        return sqlSession.selectList("post.findAllPosts", id);
    }

    public List<PostVo> findByCategories(String id, Long categoryNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("categoryNo", categoryNo);

        return sqlSession.selectList("post.findByCategoryAndId", params);
    }

    public List<PostVo> findByPostNo(Long postNo) {
        return sqlSession.selectList("post.findByPostNo", postNo);
    }

    public Long findDefaultNo(String id) {
        return sqlSession.selectOne("post.findDefaultNo", id);
    }

    public void updateCategoryToDefault(Long defaultNo, Long categoryNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("defaultCategoryNo", defaultNo);
        params.put("oldCategoryNo", categoryNo);

        sqlSession.update("post.updateToDefault", params);
    }
}
