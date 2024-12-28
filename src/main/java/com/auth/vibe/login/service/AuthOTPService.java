package com.auth.vibe.login.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Data
public class AuthOTPService {

    private static HashMap<String, String> otp_data = new HashMap<>();


    public String generateOTP(String username) {
        String otp = String.valueOf((int) (Math.random() * 9000 + 1000));
        otp_data.put(username, otp);
        return otp;
    }
    public boolean validateOTP(String username, String otp) {
        if (otp_data.containsKey(username)) {
            if (otp_data.get(username).equals(otp)) {
                otp_data.remove(username);
                return true;
            }
        }
        return false;
    }


}
