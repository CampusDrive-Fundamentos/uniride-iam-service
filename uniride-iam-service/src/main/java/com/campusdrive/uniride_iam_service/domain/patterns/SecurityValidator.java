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

    public boolean hasCleanCriminalRecords(String dni, String culCertificate) {
        // Simulación de consulta centralizada de antecedentes (CUL)
        if (dni == null || culCertificate == null) return false;
        // En un proyecto real, aquí se consultaría una API externa.
        // Para el proyecto universitario, validamos que no esté vacío y tenga formato básico.
        return !culCertificate.isBlank() && culCertificate.length() > 5;
    }

    public boolean isTiuValid(String tiuPhoto) {
        // Simulación de validación de TIU
        if (tiuPhoto == null || tiuPhoto.isBlank()) return false;
        // En un proyecto real, se usaría OCR o verificación manual.
        return true; 
    }
}
