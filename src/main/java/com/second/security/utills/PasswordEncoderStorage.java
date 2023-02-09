package com.second.security.utills;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PasswordEncoderStorage {
    public static final class ENCODER{
        public static final String BCRYPT = "bcrypt";
        public static final String PBKDF2 = "pbkdf2";
        public static final String SCRPYT = "scrypt";
    }
    public static final String DEFAULT_ENCODER = ENCODER.PBKDF2;

    Map<String, PasswordEncoder> encoders;

    public PasswordEncoderStorage(){
        encoders = new HashMap<>();
        encoders.put(ENCODER.BCRYPT, new BCryptPasswordEncoder());
        Pbkdf2PasswordEncoder pbkdf2_encoder = new Pbkdf2PasswordEncoder();
        pbkdf2_encoder.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        encoders.put(ENCODER.PBKDF2, pbkdf2_encoder);
        encoders.put(ENCODER.SCRPYT, new SCryptPasswordEncoder());
    }

    public PasswordEncoder getPasswordEncoder(){
        return new DelegatingPasswordEncoder(DEFAULT_ENCODER, encoders);
    }

    public PasswordEncoder getPasswordEncoder(String encoder_name){
        return new DelegatingPasswordEncoder(encoder_name, encoders);
    }
}
