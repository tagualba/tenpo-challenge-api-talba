package com.tenpo.challenge.utils;

import com.tenpo.challenge.validators.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

    JwtUtil jwtUtil;

    @BeforeEach
    public void init(){
        jwtUtil = new JwtUtil("LEKDLSKIEODPSOEIDK39", 3600000L);
    }

    @Test
    public void generateAndTokenOk(){
        String email = "t@gmail.com";

        String res = jwtUtil.createApiToken(email);

        assertTrue(jwtUtil.validate(res));
    }
}
