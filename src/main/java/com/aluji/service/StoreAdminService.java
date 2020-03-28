package com.aluji.service;

import com.aluji.dao.IStoreAdminDao;
import com.aluji.dao.IStreetAdminDao;
import com.aluji.entities.StoreAdmin;
import com.aluji.entities.StreetAdmin;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class StoreAdminService {
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

    public StoreAdmin getStoreAdminById(Integer adminid){
        sqlSession = sqlSessionFactory.openSession();
        StoreAdmin storeAdmin = null;
        try {
            // 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
            // 第二个参数：：这指定传入sql的参数里是用户id
            IStoreAdminDao iStoreAdminDao = sqlSession.getMapper(IStoreAdminDao.class);
            storeAdmin = iStoreAdminDao.getStoreAdminByAdminId(adminid);

        } finally {
            sqlSession.close();
        }
        return storeAdmin;
    }
}
