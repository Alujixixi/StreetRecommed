package com.aluji.service;

import com.aluji.dao.IStoreDao;
import com.aluji.dao.IStreetDao;
import com.aluji.entities.Store;
import com.aluji.entities.Street;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class StreetService {
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

    public List<Street> getAllstreet() throws IOException {
        List<Street> list = null;
        sqlSession = sqlSessionFactory.openSession();
        try {
            // 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
            // 第二个参数：指定传入sql的参数：这里是用户id
            IStreetDao istreetDao = sqlSession.getMapper(IStreetDao.class);
            list = istreetDao.findAllStreet();
            for (Street s:list) {
                System.out.println(s);

            }
        } finally {
            sqlSession.close();
        }

        return list;
    }

    public Street getStreetById (Integer streetid) throws IOException {
        Street street = null;
        sqlSession = sqlSessionFactory.openSession();
        try {
            // 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
            // 第二个参数：指定传入sql的参数：这里是用户id
            IStreetDao istreetDao = sqlSession.getMapper(IStreetDao.class);
            street = istreetDao.getStreetById(streetid);
        } finally {
            sqlSession.close();
        }
        return street;
    }
}
