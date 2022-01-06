package com.example.springblog.services;

import com.example.springblog.entities.User;

public interface IAuthenticationService {
    User signInAndReturnJWT(User user);
    User getSignedInUser();
}
