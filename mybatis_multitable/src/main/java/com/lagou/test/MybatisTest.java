package com.lagou.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.mapper.IOrderMapper;
import com.lagou.mapper.IUserMapper;
import com.lagou.mapper.UserMapper;
import com.lagou.pojo.Order;
import com.lagou.pojo.Role;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    @Test
    public void Test1() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IOrderMapper mapper = sqlSession.getMapper(IOrderMapper.class);
        List<Order> orderAndUser = mapper.findOrderAndUser();
        orderAndUser.forEach(System.out::println);
    }

    @Test
    public void Test2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        List<User> userList = mapper.findAll();
        userList.forEach(System.out::println);
    }

    @Test
    public void Test3() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        List<User> roleList = mapper.findAllUserAndRole();
        for (User user : roleList) {
            System.out.println(user);
        }
    }

    private IUserMapper userMapper;
    private IOrderMapper orderMapper;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(IUserMapper.class);
        orderMapper = sqlSession.getMapper(IOrderMapper.class);
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setId(3);
        user.setUsername("测试");
        userMapper.addUser(user);
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(3);
        user.setUsername("修改测试");
        userMapper.updateUser(user);
    }

    @Test
    public void selectUser(){
        List<User> users = userMapper.selectUser();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void deleteUser(){
        userMapper.deleteUser(3);
    }

    @Test
    public void oneToOne(){
        List<Order> orderAndUser = orderMapper.findOrderAndUser();
        for (Order order : orderAndUser) {
            System.out.println(order);
        }
    }

    @Test
    public void oneToMany(){
        List<User> all = userMapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void ManyToMany(){
        List<User> allUserAndRole = userMapper.findAllUserAndRole();
        for (User user : allUserAndRole) {
            System.out.println(user);
        }
    }

    @Test
    public void pageHelperTest(){
        PageHelper.startPage(1,1);
        List<User> users = userMapper.selectUser();
        for (User user : users) {
            System.out.println(user);
        }
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        System.out.println("总条数："+userPageInfo.getTotal());
        System.out.println("总页数："+userPageInfo.getPages());
        System.out.println("当前页："+userPageInfo.getPageNum());
        System.out.println("每页显示的条数："+userPageInfo.getSize());
    }

    @Test
    public void mapperTest() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User t = new User();
        t.setId(1);
        mapper.selectOne(t);


        //example方法
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id",1);
        List<User> users = mapper.selectByExample(example);
        for (User user : users) {
            System.out.println(user);
        }
    }



}
