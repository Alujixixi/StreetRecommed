package com.aluji.dao;

import com.aluji.entities.StreetAdmin;

public interface IStreetAdminDao {

    public boolean isStreetAdmin(Integer adminid);

    public StreetAdmin getStreetAdminByAdminId(Integer adminid);
}
