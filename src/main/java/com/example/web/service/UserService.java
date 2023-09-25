package com.example.web.service;

import com.example.web.entity.User;
import com.example.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String update(User user,User userInfo, long id) {

        if(userInfo.getEmail()!=null && userInfo.getName()!=null && userInfo.getLocalDate()!=null)
        {
            user.setName(userInfo.getName());
            user.setEmail(userInfo.getEmail());
            user.setLocalDate(userInfo.getLocalDate());
            userRepository.save(user);
            return "data updated successfully";
        }
            return " Please provide email , name and local date with valid id in url then try to update";

    }
}
