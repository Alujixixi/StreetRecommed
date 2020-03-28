package com.aluji.dao;

import com.aluji.entities.Item;

import java.util.List;

public interface IItemDao {

    public List<Item> getItemsBystoretId(Integer storeid);

    public List<Item> getItemBystoreName(String storename);
}
