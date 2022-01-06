package com.example.springblog.services;

import com.example.springblog.entities.User;

import java.util.Optional;

public interface IUserService {

    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void makeAdmin(String username);

    void makeEditor(String username);

//    void updateUserRights(String username, boolean canPost);

}
