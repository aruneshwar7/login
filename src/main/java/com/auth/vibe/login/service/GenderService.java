package com.auth.vibe.login.service;

import com.auth.vibe.login.exception.ApiExpection;
import com.auth.vibe.login.model.GenderModel;
import com.auth.vibe.login.payload.GenderRequestDTO;
import com.auth.vibe.login.payload.GenderResponseDTO;
import com.auth.vibe.login.repo.GenderRepo;
import jakarta.persistence.Id;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderService {
    @Autowired
    GenderRepo genderRepo;
    @Autowired
    ModelMapper modelMapper;

    public GenderResponseDTO getGender() {
        List<GenderModel> genderModels = genderRepo.findAll();
        return new GenderResponseDTO(genderModels);
    }
    public GenderRequestDTO addGender(GenderModel genderModel) {
        try {
            GenderModel addedGender = genderRepo.save(genderModel);
            return modelMapper.map(addedGender, GenderRequestDTO.class);
        } catch (Exception e) {
            throw new ApiExpection("bad credentials");
        }

    }
    public GenderRequestDTO updateGender(Long id, GenderModel genderModel) {
        GenderModel existingGenderModel = genderRepo.findById(id).orElseThrow(()->new ApiExpection("gender not found"));
        existingGenderModel.setName(genderModel.getName());
        GenderModel  updatedGenderModel = genderRepo.save(existingGenderModel);
        return modelMapper.map(updatedGenderModel, GenderRequestDTO.class);
    }
    public GenderRequestDTO deleteGender(Long id) {
        GenderModel existing = genderRepo.findById(id).orElseThrow(() -> new ApiExpection("gemder not found"));
        genderRepo.delete(existing);
        return modelMapper.map(existing, GenderRequestDTO.class);
    }
}
