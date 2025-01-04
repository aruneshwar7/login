package com.auth.vibe.login.repo;

import com.auth.vibe.login.model.GenderModel;
import com.auth.vibe.login.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepo extends JpaRepository<GenderModel, Long> {
}
