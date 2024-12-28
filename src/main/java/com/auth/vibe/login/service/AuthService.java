package com.auth.vibe.login.service;

import com.auth.vibe.login.exception.ApiExpection;
import com.auth.vibe.login.exception.DuplicateFieldException;
import com.auth.vibe.login.model.RoleModel;
import com.auth.vibe.login.repo.RoleRepo;
import com.auth.vibe.login.security.jwt.JwtUtils;
import com.auth.vibe.login.security.payload.*;
import com.auth.vibe.login.model.UserModel;
import com.auth.vibe.login.repo.UserRepo;
import com.auth.vibe.login.security.security_service.AuthUtil;
import com.auth.vibe.login.security.security_service.UserDetailImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    ModelMapper modelMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    private  JwtUtils jwtUtils;
    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private  RoleRepo roleRepo;

    @Autowired
    AuthOTPService otpService;

    @Autowired
    AuthUtil authUtil;
    public UserSignUpDTO createUser(UserModel userModel) {
        List<String> errors = varifyIsUserExist(userModel);
        if (!errors.isEmpty()) {
            throw new DuplicateFieldException(errors);
        }
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        List<RoleModel> roles = new ArrayList<>();
        RoleModel roleModel = roleRepo.findByRoleName("ROLE_USER");
        roles.add(roleModel);
        userModel.setRoles(roles);
        userRepo.save(userModel);
        return modelMapper.map(userModel, UserSignUpDTO.class);
    }

    public LogInDTO logIn(SignInDTO signInDTO) {
        Authentication authentication;
        try {
            if (  userRepo.findByUsername(signInDTO.getUsername()) == null) {
            throw new ApiExpection("Username does not exist");
            }
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(signInDTO.getUsername(), signInDTO.getPassword()));
        }
        catch (UsernameNotFoundException e) {
            return new LogInDTO("User does not exist", "");

        }
        catch (BadCredentialsException e) {
            throw new ApiExpection("Bad credentials");
        } catch (AuthenticationException e) {
            throw new ApiExpection("Authentication error");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        return new LogInDTO("success",jwtToken);
    }

    public String reset(ResetDto resetDto) {
        UserModel userModel = null;

        if (resetDto.getEmail() != null && authUtil.isEmail(resetDto.getEmail())) {
            userModel = userRepo.findByEmail(resetDto.getEmail());
            if (userModel == null) {
                throw new ApiExpection("Email ID not found");
            }
        } else if (resetDto.getPhone() != null && authUtil.isPhone(resetDto.getPhone())) {
            userModel = userRepo.findByPhone(resetDto.getPhone());
            if (userModel == null) {
                throw new ApiExpection("Phone number not found");
            }
        } else if (resetDto.getUsername() != null) {
            userModel = userRepo.findByUsername(resetDto.getUsername());
            if (userModel == null) {
                throw new ApiExpection("Username not found");
            }
        } else {
            throw new ApiExpection("Username, email, or phone number is required to reset password");
        }

        String otp = otpService.generateOTP(userModel.getUsername());
        return otp;
    }


    public String validateOtp(ValidateOtpDTO validateOtpDTO){
        if(otpService.validateOTP(validateOtpDTO.getUsername(),validateOtpDTO.getOtp())){
            String jwtToken = jwtUtils.generateTokenWithContent(validateOtpDTO.getUsername(),"RESET_PASSWORD",3);
            return jwtToken;
        } else {
            throw new ApiExpection("OTP is invalid");
        }
    }

    public PasswordChangedResposeDTO changePassword(ChangePasswordDTO changePasswordDTO) {
        PasswordChangedResposeDTO passwordChangedResposeDTO = new PasswordChangedResposeDTO();
        if (jwtUtils.validateJwtTokenWithAction(changePasswordDTO.getToken())) {
                String userName = jwtUtils.getUserNameFromJwtToken(changePasswordDTO.getToken());
                UserModel userModel = userRepo.findByUsername(userName);
                if(changePasswordDTO.getNewPassword() != null){
                    userModel.setPassword(changePasswordDTO.getNewPassword());
                    userRepo.save(userModel);
                    passwordChangedResposeDTO.setMessage("password changed");
                } else {
                    passwordChangedResposeDTO.setMessage("login with opt");
                }
                String jwtToken = jwtUtils.generateTokenFromUsername(new UserDetailImp(userModel));
                passwordChangedResposeDTO.setToken(jwtToken);
                return  passwordChangedResposeDTO;
        }
        throw new ApiExpection("invalid token");
    }

    private List<String> varifyIsUserExist(UserModel userModel) {
        List<String> errors = new ArrayList<>();
        if ( userModel.getUsername() != null &&  userRepo.findByUsername(userModel.getUsername()) != null) {
            errors.add("Username already exist");
        }
        if (userModel.getEmail() != null && userRepo.findByEmail(userModel.getEmail()) != null) {
            errors.add("Email already exist");
        }
        if (userModel.getPhone() != null && userRepo.findByPhone(userModel.getPhone()) != null) {
            errors.add("Phone already exist");
        }
        return errors;
    }
}
