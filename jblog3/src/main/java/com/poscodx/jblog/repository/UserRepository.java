package com.poscodx.jblog.repository;

import com.poscodx.jblog.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final SqlSession sqlSession;

    public void insert(UserVo vo) {
        sqlSession.insert("user.insert", vo);
    }

    public UserVo findById(String id) {
        return sqlSession.selectOne("user.findById", id);
    }

    public UserVo findByName(String name) {
        return sqlSession.selectOne("user.findByName", name);
    }

    public UserVo findByIdAndPassword(String id, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("password", password);
        return sqlSession.selectOne("user.findByIdAndPassword", params);
    }
}
