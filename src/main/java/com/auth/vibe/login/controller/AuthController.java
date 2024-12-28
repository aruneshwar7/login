package com.auth.vibe.login.controller;

import com.auth.vibe.login.security.jwt.JwtUtils;
import com.auth.vibe.login.security.payload.*;
import com.auth.vibe.login.model.UserModel;
import com.auth.vibe.login.service.AuthService;
import com.auth.vibe.login.service.TemplateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AuthController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthService authService;
    @Autowired
    TemplateService templateService;

    @PostMapping("/auth/signup")
    public ResponseEntity<UserSignUpDTO> signup(@RequestBody  @Valid  UserModel userModel) {
        UserSignUpDTO userSignUpDTO = authService.createUser(userModel);
        return new ResponseEntity<>(userSignUpDTO,HttpStatus.OK);
    }
    @PostMapping("/auth/login")
        public ResponseEntity<LogInDTO> login(@RequestBody @Valid SignInDTO userModel) {
        LogInDTO logInDTO= authService.logIn(userModel);
        return new ResponseEntity<>(logInDTO,HttpStatus.OK);
    }


    @PostMapping("/auth/reset")
    public ResponseEntity<String> loginUsingOtp(@RequestBody ResetDto resetDto) {
        String message = authService.reset(resetDto);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PostMapping("/auth/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestBody @Valid  ValidateOtpDTO validateOtpDTO) {
        String message = authService.validateOtp(validateOtpDTO);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PostMapping ("/auth/change-password")
    public ResponseEntity<PasswordChangedResposeDTO> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO){
        PasswordChangedResposeDTO message = authService.changePassword(changePasswordDTO);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PostMapping("/auth/login-using-otp")
    public ResponseEntity<PasswordChangedResposeDTO> loginUsingOtp(@RequestBody  ChangePasswordDTO changePasswordDTO) {
        PasswordChangedResposeDTO message = authService.changePassword(changePasswordDTO);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }


    @GetMapping(value = "/auth/signup", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getSignUp()  {
        try {
            String templateString = templateService.render("signup",null);
            return templateString;
        } catch (IOException e) {
            e.printStackTrace();
            return "404";
        }
    }
    @GetMapping(value = "/auth/login", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getLogin()  {
        try {
            String templateString = templateService.render("login",null);
            return templateString;
        } catch (IOException e) {
            e.printStackTrace();
            return "404";
        }
    }

    @GetMapping( value = "/auth/forgot-password" , produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getForgotPassword()  {
        try {
            String templateString = templateService.render("forgot_password",null);
            return templateString;
        } catch (IOException e) {
            e.printStackTrace();
            return "404";
        }
    }




    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminlogin(){
        return new ResponseEntity<>("Hello admin",HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/user", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String userlogin(){
        try {
            String templateString = templateService.render("user",null);
            return templateString;
        } catch (IOException e) {
            e.printStackTrace();
            return "404";
        }
    }



}
