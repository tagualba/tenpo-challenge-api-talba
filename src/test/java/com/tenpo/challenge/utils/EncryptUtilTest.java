package com.tenpo.challenge.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EncryptUtilTest {


    @Test
    public void encryptAndValidTokenOk(){
        EncryptUtil encryptUtil = new EncryptUtil();
        String email = "email@.com";
        String pass = "Pppp.4449";
        String hash = encryptUtil.createHash(email, pass);

        assertTrue(encryptUtil.checkHash(email, pass, hash));
    }
}
