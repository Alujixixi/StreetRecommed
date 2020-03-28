package com.aluji.dao;

import com.aluji.entities.Street;

import java.util.List;

public interface IStreetDao {

    public List<Street> findAllStreet();

    public Street getStreetById(Integer id);

    public void addStreet(Street street);

    public void updateStreet(Street street);

    public void deleteStreet(Street street);
}
