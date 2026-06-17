package com.campusdrive.uniride_iam_service.application.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DriverSignUpRequest {
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String password;
    
    @NotBlank
    private String phoneNumber;
    
    @NotBlank
    private String dni;
    
    @NotBlank
    private String culCertificate;

    private String cardNumber;

    @NotNull
    @Valid
    private VehicleRequest vehicle;
}
