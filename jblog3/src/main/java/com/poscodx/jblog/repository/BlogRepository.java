package com.poscodx.jblog.repository;

import com.poscodx.jblog.vo.BlogVo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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
}
