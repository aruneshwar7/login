package com.auth.vibe.login.security.security_service;

import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    public boolean isEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }

    public boolean isPhone(String phone){
        String phoneRegex = "^\\d{10}$";
        return phone != null && phone.matches(phoneRegex);
    }


}
