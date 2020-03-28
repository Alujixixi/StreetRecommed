package com.aluji.dao;

import com.aluji.entities.StoreAdmin;
import com.aluji.entities.StreetAdmin;

public interface IStoreAdminDao {

    public boolean isStoreAdmin(Integer adminid);

    public StoreAdmin getStoreAdminByAdminId(Integer adminid);

}
