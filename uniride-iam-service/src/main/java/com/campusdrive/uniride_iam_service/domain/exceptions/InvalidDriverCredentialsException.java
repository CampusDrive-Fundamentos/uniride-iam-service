package com.campusdrive.uniride_iam_service.domain.exceptions;

public class InvalidDriverCredentialsException extends RuntimeException {
    public InvalidDriverCredentialsException(String message) {
        super(message);
    }
}
