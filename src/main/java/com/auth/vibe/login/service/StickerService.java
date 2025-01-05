package com.auth.vibe.login.service;

import com.auth.vibe.login.exception.ApiExpection;
import com.auth.vibe.login.model.StickerModel;
import com.auth.vibe.login.payload.StickerResponseDto;
import com.auth.vibe.login.repo.StickerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StickerService {
    @Autowired
    StickerRepo stickerRepo;
    public StickerResponseDto getModel(){
        try{
        List<StickerModel> modelList = stickerRepo.findAll();
        return new StickerResponseDto(modelList);
        } catch (Exception e) {
        throw new ApiExpection("bad credentials");
    }
    }

    public StickerModel addModel(StickerModel modelModel){
        try {
            StickerModel addedModel = stickerRepo.save(modelModel);
            return addedModel;
        } catch (Exception e) {
            throw new ApiExpection("bad credentials");
        }
    }

    public StickerModel updateModel(Long id, StickerModel modelModel) {
        StickerModel existingModel = stickerRepo.findById(id).orElseThrow(()->new ApiExpection("model not found"));
        if (modelModel.getName() != null)
            existingModel.setName(modelModel.getName());
        if (modelModel.getImageUrl() != null)
             existingModel.setImageUrl(modelModel.getImageUrl());

        StickerModel updatedModel = stickerRepo.save(existingModel);
        return updatedModel;
    }
    public StickerModel deleteModel(Long id) {
        StickerModel existing = stickerRepo.findById(id).orElseThrow(() -> new ApiExpection("model not found"));
        stickerRepo.delete(existing);
        return existing;
    }
}
