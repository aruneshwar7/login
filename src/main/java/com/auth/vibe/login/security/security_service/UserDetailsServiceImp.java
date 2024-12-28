package com.auth.vibe.login.security.security_service;

import com.auth.vibe.login.model.UserModel;
import com.auth.vibe.login.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepo.findByUsername(username);
        if(userModel != null){
            return new UserDetailImp(userModel);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
