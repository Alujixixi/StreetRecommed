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
import java.security.spec.ECField;

@Service
public class RegisterService {
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

    public boolean alreadyhasUser(String username) throws IOException {
        User user = null;
        sqlSession = sqlSessionFactory.openSession();
        try {
            // 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
            // 第二个参数：指定传入sql的参数：这里是用户id
            IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
            user = iUserDao.getUserByName(username);

        } finally {
            sqlSession.close();
        }
        if(user == null)
            return false;
        else
            return true;
    }

    public void addUser(User user) throws IOException {

        sqlSession = sqlSessionFactory.openSession();
        try {
            // 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
            // 第二个参数：指定传入sql的参数：这里是用户id
            IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
            iUserDao.addUser(user);

        }catch (Exception e
        ){
            System.out.println("Insert Exception");
        }
        finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }
}
