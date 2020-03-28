package com.aluji.dao;


import com.aluji.entities.Rating;
import com.aluji.entities.RecommendList;
import com.aluji.entities.Store;
import com.aluji.entities.StoreClicks;

import java.util.List;
import java.util.Map;
import java.util.Random;

public interface IRatingDao {

    //更新店铺点击数据
    void updateStoreClicks(List<StoreClicks> storeClicks);

    void updateStoreRating(List<Rating> ratings);

    //获得所有点击数据
    List<StoreClicks> getAllStoreClicks();

    List<Integer> getUserList();
    List<Integer> getStoreList();

    List<StoreClicks> getStoreClicksByUserId(Integer userId);

    //*
    List<Map<Integer,List<Integer>>> getAllRecommendList();

    RecommendList getRecommendListByUserId(Integer userId);

    Integer getStore1(Integer userId);
    Integer getStore2(Integer userId);
    Integer getStore3(Integer userId);
    Integer getStore4(Integer userId);
    Integer getStore5(Integer userId);
    Integer getStore6(Integer userId);
    Integer getStore7(Integer userId);

    Integer getStoreNum();
}
