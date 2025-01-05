package com.auth.vibe.login.controller;

import com.auth.vibe.login.model.StickerModel;
import com.auth.vibe.login.payload.StickerResponseDto;
import com.auth.vibe.login.service.StickerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StickerController {
    @Autowired
    StickerService stickerService;

    @GetMapping("/api/admin/sticker")
    public ResponseEntity<StickerResponseDto> getModel(){
        StickerResponseDto stickerResponseDto = stickerService.getModel();
        return ResponseEntity.ok(stickerResponseDto);
    }
    @PostMapping("/api/admin/sticker")
    public ResponseEntity<StickerModel> addModel(@RequestBody @Valid StickerModel modelModel){
        StickerModel model = stickerService.addModel(modelModel);
        return ResponseEntity.ok(model);
    }
    @PutMapping("/api/admin/sticker/{id}")
    public ResponseEntity<StickerModel> updateModel(@PathVariable Long id, @RequestBody @Valid StickerModel modelModel){
        StickerModel model = stickerService.updateModel(id, modelModel);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/api/admin/sticker/{id}")
    public ResponseEntity<StickerModel> deleteModel(@PathVariable Long id){
        StickerModel model = stickerService.deleteModel(id);
        return ResponseEntity.ok(model);
    }
}
