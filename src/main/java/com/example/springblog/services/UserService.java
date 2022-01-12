package com.example.springblog.services;

import com.example.springblog.entities.Role;
import com.example.springblog.entities.User;
import com.example.springblog.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setCreate_time(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User originalUser = userRepository.findByUsername(user.getUsername()).get();
        user.setId(originalUser.getId());
        user.setRole(originalUser.getRole());                                               //making sure these properties are unchangeable, especially the role
        user.setCreate_time(originalUser.getCreate_time());                                 //allowing users to change their role would pose a huge security risk, so it is cross-checked with the record in the database
        user.setLast_login(originalUser.getLast_login());
        user.setUser_articles(originalUser.getUser_articles());

        if(user.getPassword() == null){
            user.setPassword(originalUser.getPassword());
        }
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void makeAdmin(String username) {
        userRepository.updateUserRole(username, Role.ADMIN);
    }

    @Override
    @Transactional
    public void makeEditor(String username) {
        userRepository.updateUserRole(username, Role.EDITOR);
    }

    @Override
    @Transactional
    public void makeUser(String username) {
        userRepository.updateUserRole(username, Role.USER);
    }

    @Override
    @Transactional
    public void updateLastLogin(String username) {
        userRepository.updateLoginTime(username, LocalDateTime.now());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        if(userRepository.findByUsername(username).isPresent()){
            userRepository.deleteByUsername(username);
        }
    }

//    @Override
//    @Transactional
//    public void updateUserRights(String username, boolean canPost) {
//        userRepository.updateUserRights(username, canPost);
//    }
}
