package com.example.springblog.services;

import com.example.springblog.entities.User;

import java.util.List;

public interface IAuthenticationService {
    User signInAndReturnJWT(User user);
    User getSignedInUser();
}
