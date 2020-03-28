package com.aluji.service;

import com.aluji.dao.IStreetAdminDao;
import com.aluji.dao.IUserDao;
import com.aluji.entities.Street;
import com.aluji.entities.StreetAdmin;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class StreetAdminService {
    InputStream inputStream = null;
    String resource = null;
    SqlSessionFactory sqlSessionFactory = null;
    SqlSession sqlSession = null;
    //    private void init() throws IOException {
//        resource = "SqlMapConfig.xml";
//        inputStream = Resources.getResourceAsStream(resource);
//        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        sqlSession = sqlSessionFactory.openSession();
//
//    }
    {
        resource = "SqlMapConfig.xml";
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public StreetAdmin getStreetAdminById(Integer adminid){
        sqlSession = sqlSessionFactory.openSession();
        StreetAdmin streetAdmin = null;
        try {
            // 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
            // 第二个参数：：这指定传入sql的参数里是用户id
            IStreetAdminDao iStreetAdminDao = sqlSession.getMapper(IStreetAdminDao.class);
            streetAdmin = iStreetAdminDao.getStreetAdminByAdminId(adminid);

        } finally {
            sqlSession.close();
        }
        return streetAdmin;
    }
}
