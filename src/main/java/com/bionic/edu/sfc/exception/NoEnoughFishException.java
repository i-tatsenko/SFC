package com.bionic.edu.sfc.exception;

/**
 * Created by docent on 23.11.14.
 */
public class NoEnoughFishException extends RuntimeException {

    public NoEnoughFishException() {
    }

    public NoEnoughFishException(String message) {
        super(message);
    }

    public NoEnoughFishException(String message, Throwable cause) {
        super(message, cause);
    }
}
