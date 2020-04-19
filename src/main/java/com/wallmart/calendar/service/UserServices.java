package com.wallmart.calendar.service;

import com.wallmart.calendar.entity.User;
import com.wallmart.calendar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }
}
