package com.auth.vibe.login.repo;

import com.auth.vibe.login.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<RoleModel, Long> {
    public RoleModel findByRoleName(String username);
}

