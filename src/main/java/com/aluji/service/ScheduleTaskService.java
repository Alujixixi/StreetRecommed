package com.aluji.service;

import com.aluji.dao.IBrowseRecordDao;
import com.aluji.dao.IRatingDao;
import com.aluji.dao.IStoreDao;
import com.aluji.dao.IUserDao;
import com.aluji.entities.Rating;
import com.aluji.entities.StoreBrowse;
import com.aluji.entities.StoreClicks;
import com.aluji.entities.UserStoreKey;
import com.google.common.collect.HashBasedTable;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
@EnableScheduling
public class ScheduleTaskService {
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

    @Autowired
    RatingCalculator ratingCalculator;

    @Scheduled(cron = "0 0 2 * * *")
    private void updateStoreClick() {
        System.out.println("updateStoreClick running------" + new Date());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IBrowseRecordDao iBrowseRecordDao = sqlSession.getMapper(IBrowseRecordDao.class);
        List<StoreBrowse> allStoreBrowse = iBrowseRecordDao.getAllStoreBrowse();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        List<Integer> allUser = iUserDao.getAllUserId();
        IStoreDao iStoreDao = sqlSession.getMapper(IStoreDao.class);
        List<Integer> allStore = iStoreDao.getAllStoreId();

        HashBasedTable<Integer,Integer,Integer> storeClickTable = HashBasedTable.<Integer, Integer, Integer>create();

        Map<UserStoreKey,Integer> map = new HashMap<>();
        for(StoreBrowse storeBrowse: allStoreBrowse) {
            UserStoreKey userStoreKey = new UserStoreKey(storeBrowse.getUserId(),storeBrowse.getStoreId());
            if(!storeClickTable.contains(storeBrowse.getUserId(),storeBrowse.getStoreId())) {
                storeClickTable.put(storeBrowse.getUserId(),storeBrowse.getStoreId(),1);
            } else {
                storeClickTable.put(storeBrowse.getUserId(),storeBrowse.getStoreId(),storeClickTable.get(storeBrowse.getUserId(),storeBrowse.getStoreId()) + 1);
            }
        }

        List<StoreClicks> storeClicks = new ArrayList<>();
        for (Integer userId : allUser) {
            for (Integer storeId : allStore) {
                if(storeClickTable.contains(userId,storeId))
                    storeClicks.add(new StoreClicks(userId,storeId,1 + storeClickTable.get(userId,storeId)));
                else
                    storeClicks.add(new StoreClicks(userId,storeId,1));
            }
        }
        IRatingDao iRatingDao = sqlSession.getMapper(IRatingDao.class);
        iRatingDao.updateStoreClicks(storeClicks);
        sqlSession.commit();
        sqlSession.close();
        System.out.println("fixedRate>>>"+new Date());
    }

    @Scheduled(cron = "0 0 2 * * *")
    private void updateRates() {
        System.out.println("updateRatesRunning --------" + new Date());
        List<Rating> userRating = ratingCalculator.calculateRanking();
        System.out.println(userRating.size());
        for (Rating rating : userRating) {
            System.out.println(rating);
        }

        SqlSession sqlSession = sqlSessionFactory.openSession();
        IRatingDao iRatingDao = sqlSession.getMapper(IRatingDao.class);
        iRatingDao.updateStoreRating(userRating);
        sqlSession.commit();
        sqlSession.close();
    }

}
