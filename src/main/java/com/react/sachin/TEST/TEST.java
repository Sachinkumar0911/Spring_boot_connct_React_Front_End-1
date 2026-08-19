package com.react.sachin.TEST;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TEST {
   public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String password = "sachin123";

        String encrypted =
                encoder.encode(password);

        System.out.println("Password: " + password);
        System.out.println("Encrypted: " + encrypted);

        System.out.println(
            encoder.matches(password, encrypted)
        );
    }  
}
