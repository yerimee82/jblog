package com.poscodx.jblog.repository;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlogRepository {
    private final SqlSession sqlSession;

    public BlogVo find(String id) {
        return sqlSession.selectOne("blog.find-blog", id);
    }

    public void update(BlogVo vo) {
        sqlSession.update("blog.update-blog", vo);
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
}
