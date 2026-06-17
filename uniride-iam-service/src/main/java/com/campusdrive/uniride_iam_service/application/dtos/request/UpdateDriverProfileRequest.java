package com.campusdrive.uniride_iam_service.application.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDriverProfileRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    
    private String cardNumber;
    private VehicleRequest vehicle;
}
