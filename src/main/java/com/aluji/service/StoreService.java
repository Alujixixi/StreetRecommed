package com.aluji.service;

import com.aluji.dao.IRatingDao;
import com.aluji.dao.IStoreDao;
import com.aluji.dao.IStreetDao;
import com.aluji.dao.IUserDao;
import com.aluji.entities.RecommendList;
import com.aluji.entities.Store;
import com.aluji.entities.Street;
import com.aluji.entities.User;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.hamcrest.core.Is;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreService {
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

    public List<Store> getAllStoreInStreet(Integer steetid) throws IOException {
        List<Store> list = null;
        sqlSession = sqlSessionFactory.openSession();
        try {
            IStoreDao istoreDao = sqlSession.getMapper(IStoreDao.class);
            list = istoreDao.getStoresBystreetId(steetid);

        } finally {
            sqlSession.close();
        }
        return list;
    }

    public Store getStoreById (Integer storeid) throws IOException {
        Store store = null;
        sqlSession = sqlSessionFactory.openSession();
        try {

            IStoreDao istoreDao = sqlSession.getMapper(IStoreDao.class);
            store = istoreDao.getStoreById(storeid);
        } finally {
            sqlSession.close();
        }
        return store;
    }

    public boolean addStore(Store store){
        System.out.println(store);
        sqlSession = sqlSessionFactory.openSession();
        try {

            IStoreDao istoreDao = sqlSession.getMapper(IStoreDao.class);
            istoreDao.addStore(store);
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
        return true;
    }

    public boolean alterStore(Store store){
        System.out.println(store);
        sqlSession = sqlSessionFactory.openSession();
        try {

            IStoreDao istoreDao = sqlSession.getMapper(IStoreDao.class);
            istoreDao.updateStore(store);
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
        return true;
    }

    public boolean deleteStore(Integer storeid){
        sqlSession = sqlSessionFactory.openSession();
        try {

            IStoreDao istoreDao = sqlSession.getMapper(IStoreDao.class);
            istoreDao.deleteStore(storeid);
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
        return true;
    }

    public List<Store> getRecommendStoreList(Integer userId) {
        List<Store> list = new ArrayList<>();

//        MysqlDataSource dataSource=new MysqlDataSource();
//        dataSource.setServerName("localhost");
//        dataSource.setUser("root");
//        dataSource.setPassword("admin");
//        dataSource.setDatabaseName("commercial_street_db");
//        JDBCDataModel jdbcDataModel = new MySQLJDBCDataModel(dataSource, "my_prefer_table", "my_user_column", "my_item_column", "my_pref_value_column", "my_timestamp_column");
//        MySQLJDBCDataModel mySQLJDBCDataModel = new MySQLJDBCDataModel();
        return list;
    }

    public List<Store> getAllStore() {
        sqlSession = sqlSessionFactory.openSession();
        IStoreDao iStoreDao = sqlSession.getMapper((IStoreDao.class));
        return iStoreDao.getAllStore();
    }

    public Store getStoreByStoreId(Integer storeId) {
        sqlSession = sqlSessionFactory.openSession();
        IStoreDao iStoreDao = sqlSession.getMapper(IStoreDao.class);
        return iStoreDao.getStoreById(storeId);
    }

    public List<Integer> getAllStoreId() {
        List<Store> stores = this.getAllStore();
        List<Integer> storeIds = new ArrayList<>();
        for(Store store: stores) {
            storeIds.add(store.getStoreId());
        }
        return storeIds;
    }

    public Map<Integer,Integer> getStreetIdToStoreId(List<Integer> storeIdList) {
        List<Store> allStore = getAllStore();
        Map<Integer,Integer> map = new HashMap<>();
        for(Store store: allStore) {
            if(storeIdList.contains(store.getStoreId())) map.put(store.getStoreId(),store.getStreetId());
        }
        return map;
    }

    public List<Integer> getRecommendStoreIdByUserId(Integer userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IRatingDao iRatingDao = sqlSession.getMapper(IRatingDao.class);
        List<Integer> list = new ArrayList<>();
        list.add(iRatingDao.getStore1(userId));
        list.add(iRatingDao.getStore2(userId));
        list.add(iRatingDao.getStore3(userId));
        list.add(iRatingDao.getStore4(userId));
        list.add(iRatingDao.getStore5(userId));
        list.add(iRatingDao.getStore6(userId));
        list.add(iRatingDao.getStore7(userId));
        return list;
    }
}
