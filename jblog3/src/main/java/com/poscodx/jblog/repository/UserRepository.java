package com.poscodx.jblog.repository;

import com.poscodx.jblog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final SqlSession sqlSession;

    public int insert(UserVo vo) {
        return sqlSession.insert("user.insert", vo);
    }

    public UserVo findById(String id) {
        return sqlSession.selectOne("user.findById", id);
    }

    public UserVo findByName(String name) {
        return sqlSession.selectOne("user.findByName", name);
    }
}
