package com.aluji.dao;

import com.aluji.entities.User;

import java.util.List;

public interface IUserDao {
    List<User> getAllUser();

    User getUserById(Integer id);

    User getUserByName(String username);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    List<Integer> getAllUserId();
}
