package com.aluji.service;

import com.aluji.dao.IRatingDao;
import com.aluji.entities.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class RatingCalculator {
    //点击按排名算
    private final static Double RANK = 0.40;
//    点击按点击数算
    private final static Double CLICKS = 0.40;
//    按热门商业街算
    private final static Double STREET = 0.20;
//    private final static Double STREET_VALUE = 0.50;

    @Autowired
    StoreService storeService;

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


    public List<Rating> calculateRanking() {
        System.out.println("in calculateRanking---------");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IRatingDao iRatingDao = sqlSession.getMapper(IRatingDao.class);
        List<StoreClicks> storeClicks = iRatingDao.getAllStoreClicks();
        List<Integer> userIds = iRatingDao.getUserList();
        List<Integer> storeIds = iRatingDao.getStoreList();
        Integer storeNum = iRatingDao.getStoreNum();
        List<Rating> res = new ArrayList<>();
        for (Integer userId : userIds) {
            System.out.println(userId);
            res.addAll(calculateRatingByUser(userId,storeNum));
        }
        return res;
    }

    private List<Rating> calculateRatingByUser(Integer userId, Integer storeNum) {
        System.out.println("In calculateRatingByUser-----------");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IRatingDao iRatingDao = sqlSession.getMapper(IRatingDao.class);
        List<StoreClicks> storeClicks = iRatingDao.getStoreClicksByUserId(userId);


        for (StoreClicks storeClick : storeClicks) {

        }


        Map<Integer,Double> ratingMap = new HashMap<>();
        int len = storeClicks.size();
        Collections.sort(storeClicks, new Comparator<StoreClicks>() {
            @Override
            public int compare(StoreClicks o1, StoreClicks o2) {
                return o1.getClicks()-o2.getClicks();
            }
        });
        Map<Integer,Integer> clicksMap = new HashMap<>();
        //用户userId的总点击次数
        Integer allClick = 0;
        for(StoreClicks storeClick: storeClicks) {
            allClick += storeClick.getClicks();
            clicksMap.put(storeClick.getStoreId(),storeClick.getClicks());
        }
        allClick = allClick + storeNum - storeClicks.size();
        if(allClick <= 0) allClick = 1;
        //计算点击得分
        List<Rating> ratingList = new ArrayList<>();
        for(StoreClicks storeClick: storeClicks) {
            ratingMap.put(storeClick.getStoreId(),CLICKS * ( (double)storeClick.getClicks()/(double)allClick) );
        }

        //计算排名得分
        for(int i = 0; i < len; i++) {
            ratingMap.put(storeClicks.get(i).getStoreId(),ratingMap.get(storeClicks.get(i).getStoreId()) + (double)(len-i)/(double)len * RANK);
        }
        calculateByStreet(userId,ratingMap);
        List<Rating> ratings = new ArrayList<>();
        Set<Map.Entry<Integer, Double>> entries = ratingMap.entrySet();
        for (Map.Entry<Integer, Double> entry : entries) {
            ratings.add(new Rating(userId,entry.getKey(),entry.getValue()));
        }
        return ratings;
    }

    // 通过商铺所在商业街关系来计算评分
    private void calculateByStreet(Integer userId,Map<Integer,Double> ratingMap) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IRatingDao iRatingDao = sqlSession.getMapper(IRatingDao.class);
        List<StoreClicks> storeClicks = iRatingDao.getStoreClicksByUserId(userId);
        List<Integer> storeIdList = new ArrayList<>();
        for (StoreClicks storeClick: storeClicks) {
            storeIdList.add(storeClick.getStoreId());
        }
        Map<Integer,Integer> storeToStreet = storeService.getStreetIdToStoreId(storeIdList);
//        寻找最多出现的商业街
        Integer popularStreetId = 0;
        int max_street_cnt = 0;
        Set<Map.Entry<Integer, Integer>> entries = storeToStreet.entrySet();
        Map<Integer,Integer> streetIdCnt = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : entries) {
            if( !streetIdCnt.containsKey(entry.getValue()))
                streetIdCnt.put(entry.getValue(),1);
            else
                streetIdCnt.put(entry.getValue(),streetIdCnt.get(entry.getValue()) + 1);
        }
        Set<Map.Entry<Integer, Integer>> entries1 = streetIdCnt.entrySet();
        for (Map.Entry<Integer, Integer> entry : entries1) {
            if(entry.getValue() > max_street_cnt) {
                max_street_cnt = entry.getValue();
                popularStreetId = entry.getKey();
            }
        }

        Set<Map.Entry<Integer, Double>> entries2 = ratingMap.entrySet();
        for (Map.Entry<Integer, Double> entry : entries2) {
            if(storeToStreet.get(entry.getKey()) == popularStreetId)
                ratingMap.put(entry.getKey(),entry.getValue() + STREET);
        }
        return;
    }
}
