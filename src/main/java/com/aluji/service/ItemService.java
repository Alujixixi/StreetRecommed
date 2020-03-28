package com.aluji.service;

import com.aluji.dao.IItemDao;
import com.aluji.dao.IStoreDao;
import com.aluji.entities.Item;
import com.aluji.entities.Store;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ItemService {
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

    public List<Item> getAllItemInStore(Integer storeid) throws IOException {
        List<Item> list = null;
        sqlSession = sqlSessionFactory.openSession();
        try {
            IItemDao iItemDao = sqlSession.getMapper(IItemDao.class);
            list = iItemDao.getItemsBystoretId(storeid);
        } finally {
            sqlSession.close();
        }
        return list;
    }
    public List<Item> getAllItemInStore(String storeName) {
        return null;
    }


}
