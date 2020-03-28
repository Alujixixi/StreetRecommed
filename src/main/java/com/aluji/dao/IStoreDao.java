package com.aluji.dao;

import com.aluji.entities.Store;

import java.util.List;

public interface IStoreDao {

    List<Store> getAllStore();

    List<Store> getStoresBystreetId(Integer id);

    Store getStoreById(Integer id);

    void addStore(Store store);

    void updateStore(Store store);

    void deleteStore(Store store);

    void deleteStore(Integer storeid);

    //Integer getStreetIdByStoreId(Integer storeId);

    List<Integer> getAllStoreId();
}
