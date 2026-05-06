package com.campusdrive.uniride_iam_service.domain.patterns;

public class SecurityValidator {
    private static SecurityValidator instance;

    private SecurityValidator() {}

    public static synchronized SecurityValidator getInstance() {
        if (instance == null) {
            instance = new SecurityValidator();
        }
        return instance;
    }

    public boolean isEmailValid(String email) {
        if (email == null) return false;
        return email.toLowerCase().endsWith(".edu") || email.toLowerCase().endsWith(".edu.pe");
    }
}
