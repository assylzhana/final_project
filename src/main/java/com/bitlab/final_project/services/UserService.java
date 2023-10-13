package com.bitlab.final_project.services;

import com.bitlab.final_project.models.User;
import com.bitlab.final_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//сервис не пишем, ибо обазначили бинов в конфиге
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null ){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void addUser(User newUser,String rePassword) {
        User user = userRepository.findByEmail(newUser.getEmail());
        if (user != null) {
            return;
        }
        if (!newUser.getPassword().equals(rePassword)) {
            return;
        }
        user.setPassword(passwordEncoder.encode(rePassword));
        if (newUser.getPassword().equals(rePassword)) {

        }
    }
}
