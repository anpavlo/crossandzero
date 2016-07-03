package com.softserve.edu.service.impl;

import org.springframework.stereotype.Service;

import com.softserve.edu.entity.User;
import com.softserve.edu.service.UserService;

@Service
public class UserServiceimpl implements UserService{

    @Override
    public User getUser() {
        
        return new User("user","user");
    }

}
