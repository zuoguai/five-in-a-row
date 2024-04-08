package com.fir.backend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreatePassword {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String raw = "123456";
        String encode  = passwordEncoder.encode(raw);
        System.out.println(encode);
    }
}
