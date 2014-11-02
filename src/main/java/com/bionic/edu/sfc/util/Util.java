package com.bionic.edu.sfc.util;

import com.bionic.edu.sfc.entity.Fish;
import com.bionic.edu.sfc.entity.Manufacturer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Comparator;

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

    public static Comparator<Manufacturer> getManufComp() {
        return (m1, m2) -> m1.getName().compareTo(m2.getName());
    }

    public static Comparator<Fish> getFishCompar() {
        return (f1, f2) -> f1.getName().compareTo(f2.getName());
    }
}
