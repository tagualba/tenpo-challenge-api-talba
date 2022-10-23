package com.tenpo.challenge.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class EncryptUtil {

    public String createHash(String email, String password) {
        return BCrypt.hashpw(email + password, BCrypt.gensalt());
    }

    public Boolean checkHash(String email, String password, String hash) {
        return BCrypt.checkpw(email.concat(password), hash);
    }

}
