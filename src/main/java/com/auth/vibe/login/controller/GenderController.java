package com.auth.vibe.login.controller;

import com.auth.vibe.login.model.GenderModel;
import com.auth.vibe.login.payload.GenderRequestDTO;
import com.auth.vibe.login.payload.GenderResponseDTO;
import com.auth.vibe.login.service.GenderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GenderController {

    @Autowired
    GenderService genderService;

    @GetMapping("/api/gender")
    public ResponseEntity<GenderResponseDTO> getGender() {

        GenderResponseDTO genderResponseDTO = genderService.getGender();
        return new ResponseEntity<>(genderResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/api/admin/gender")
    public ResponseEntity<GenderRequestDTO> addGender(@RequestBody @Valid  GenderModel genderModel) {
        GenderRequestDTO genderRequestDTO = genderService.addGender(genderModel);
        return new ResponseEntity<>(genderRequestDTO, HttpStatus.CREATED);
    }

    @PutMapping("/api/admin/gender/{id}")
    public ResponseEntity<GenderRequestDTO> updateGender(@PathVariable Long id, @RequestBody @Valid GenderModel genderModel) {
        GenderRequestDTO genderRequestDTO = genderService.updateGender(id, genderModel);
        return new ResponseEntity<>(genderRequestDTO, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/gender/{id}")
    public ResponseEntity<GenderRequestDTO> deleteGender(@PathVariable Long id) {
        GenderRequestDTO deletedGender  = genderService.deleteGender(id);
        return new ResponseEntity<>(deletedGender, HttpStatus.OK);
    }
}
