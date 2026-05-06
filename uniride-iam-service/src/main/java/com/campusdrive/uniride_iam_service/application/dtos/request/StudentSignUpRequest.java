package com.campusdrive.uniride_iam_service.application.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentSignUpRequest {
    @NotBlank
    private String username;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String password;
}
