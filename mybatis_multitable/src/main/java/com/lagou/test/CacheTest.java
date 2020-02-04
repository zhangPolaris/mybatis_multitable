package com.lagou.test;

import com.lagou.mapper.IOrderMapper;
import com.lagou.mapper.IUserMapper;
import com.lagou.pojo.User;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class CacheTest {
    private IUserMapper userMapper;
    private SqlSession sqlSession;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(IUserMapper.class);
    }

    @Test
    public void firstLevelCache(){
        //第一次查询id为1的用户
        User userById1 = userMapper.findUserById(1);

        //更新用户
        User user = new User();
        user.setId(1);
        user.setUsername("tom");
        userMapper.updateUser(user);
        sqlSession.commit();
        //手动刷新缓存
        sqlSession.clearCache();
        //第二次查询id为1的用户
        User userById2 = userMapper.findUserById(1);

        System.out.println(userById1==userById2);

    }
}
