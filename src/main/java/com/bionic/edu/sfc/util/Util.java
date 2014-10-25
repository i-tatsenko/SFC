package com.bionic.edu.sfc.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Ivan
 * 2014.10
 */
public abstract class Util {
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private Util() { }

    public static String getPasswordHash(String password) {
        return PASSWORD_ENCODER.encode(password);
    }

    public static boolean checkCredentials(String password, String passwordHash) {
        return PASSWORD_ENCODER.matches(password, passwordHash);
    }
}
