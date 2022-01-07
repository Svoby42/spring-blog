package com.example.springblog.services;

import com.example.springblog.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User saveUser(User user);

    User updateUser(User user);

    Optional<User> findByUsername(String username);

    void makeAdmin(String username);

    void makeEditor(String username);

    void makeUser(String username);         //used for revoking posting rights

    List<User> getAllUsers();

    void deleteUser(String username);

    void updateLastLogin(String username);


//    void updateUserRights(String username, boolean canPost);

}
