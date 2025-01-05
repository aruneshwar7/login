package com.auth.vibe.login.repo;

import com.auth.vibe.login.model.InventoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
public interface InventoryRepo extends JpaRepository<InventoryModel, Long> {

}
