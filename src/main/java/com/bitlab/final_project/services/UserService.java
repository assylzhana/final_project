package com.bitlab.final_project.services;

import com.bitlab.final_project.models.User;
import com.bitlab.final_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService   {

    @Autowired
    private UserRepository userRepository;


    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }

}
