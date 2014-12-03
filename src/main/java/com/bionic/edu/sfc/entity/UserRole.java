package com.bionic.edu.sfc.entity;

/**
 * Ivan
 * 2014.10
 */
public enum UserRole {
    ROLE_CUSTOMER,
    ROLE_GENERAL_MANAGER,
    ROLE_COLD_STORE_MANAGER,
    ROLE_ACCOUNTANT,
    ROLE_SECURITY_OFFICER;

    public static UserRole safeValueOf(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
