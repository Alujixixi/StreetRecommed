package com.aluji.service;

import com.aluji.dao.IBrowseRecordDao;
import com.aluji.entities.StoreBrowse;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrowseService {
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


    //插入商业街浏览记录
    public void setStreetBrowseRecord(Integer userid, Integer streetid, String time){
        if(userid==null) return;
        Map<String,Object> parm=new HashMap<>();
        parm.put("userid",userid);
        parm.put("streetid",streetid);
        parm.put("time",time);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        IBrowseRecordDao iBrowseRecordDao = sqlSession.getMapper(IBrowseRecordDao.class);
        iBrowseRecordDao.insertstreetBrowseRecord(parm);
        sqlSession.commit();
    }

    //插入商店浏览记录
    public void setStoreBrowseRecord(Integer userid, Integer storeid, String time){


        Map<String,Object> parm=new HashMap<>();
        parm.put("userid", userid);
        parm.put("storeid",storeid);
        parm.put("time",time);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        IBrowseRecordDao iBrowseRecordDao = sqlSession.getMapper(IBrowseRecordDao.class);
        iBrowseRecordDao.insertstoreBrowseRecord(parm);
        sqlSession.commit();
    }

    public List<StoreBrowse> getAllstoreBrowseBystreetId(Integer streetid){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IBrowseRecordDao iBrowseRecordDao = sqlSession.getMapper(IBrowseRecordDao.class);
        List<StoreBrowse> list = iBrowseRecordDao.getAllstoreBrowseRecordBystreetId(streetid);
        return list;
    }

    public List<StoreBrowse> getAllStoreBrowse() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IBrowseRecordDao iBrowseRecordDao = sqlSession.getMapper(IBrowseRecordDao.class);
        return iBrowseRecordDao.getAllStoreBrowse();
    }
}
