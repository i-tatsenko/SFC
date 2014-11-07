package com.bionic.edu.sfc.util;

import com.bionic.edu.sfc.entity.Fish;
import com.bionic.edu.sfc.entity.FishParcel;
import com.bionic.edu.sfc.entity.FishShipSupply;
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

    public static Comparator<Manufacturer> getManufComparator() {
        return (m1, m2) -> m1.getName().compareTo(m2.getName());
    }

    public static Comparator<Fish> getFishComparator() {
        return (f1, f2) -> f1.getName().compareTo(f2.getName());
    }

    public static Comparator<FishParcel> getFishParcelComparator() {
        return (fp1, fp2) -> (int)(fp1.getId() - fp2.getId());
    }

    public static Comparator<FishShipSupply> getFishShipSupplyComparator() {
        return (r1, r2) -> {
            long timeDiff = r2.getCreationDate().getTime() - r1.getCreationDate().getTime();
            if (timeDiff == 0) {
                return r1.getSupplyCode().compareTo(r2.getSupplyCode());
            }
            return (int) timeDiff;
        };
    }
}
