package com.auth.vibe.login.repo;

import com.auth.vibe.login.model.StickerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepo extends JpaRepository<StickerModel, Long> {
}
