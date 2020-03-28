package com.aluji.service;

import com.aluji.dao.IUserDao;
import com.aluji.entities.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    InputStream inputStream = null;
    String resource = null;
    SqlSessionFactory sqlSessionFactory = null;
    SqlSession sqlSession = null;
    {
        resource = "SqlMapConfig.xml";
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }


    public Integer getuserIdByuserName(String username){
        if(username.equals("游客用户")||username==null) return null;
        sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        User user = iUserDao.getUserByName(username);
        return user.getUserId();
    }

    public List<User> getAllUser() {
        sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper((IUserDao.class));
        return iUserDao.getAllUser();
    }

    public List<Integer> getAllUserId() {
        List<User> users = this.getAllUser();
        List<Integer> userIds = new ArrayList<>();
        for(User user: users) {
            userIds.add(user.getUserId());
        }
        return userIds;
    }



}
