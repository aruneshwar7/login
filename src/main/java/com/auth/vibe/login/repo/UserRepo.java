package com.auth.vibe.login.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import com.auth.vibe.login.model.UserModel;

public interface UserRepo extends JpaRepository<UserModel, Long> {
    public  UserModel findByUsername(String username);
    public UserModel findByEmail(String email);
    public UserModel findByPhone(String phone);
}
