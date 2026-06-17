package com.campusdrive.uniride_iam_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    private Long id;
    private String dni;
    private String licenseNumber;
    private String culCertificate; // Certificado de Antecedentes Penales
    private boolean isActive;
    private User user;
    private String cardNumber;
    private Vehicle vehicle;
}
