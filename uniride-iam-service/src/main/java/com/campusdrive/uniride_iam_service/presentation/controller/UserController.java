package com.campusdrive.uniride_iam_service.presentation.controller;

import com.campusdrive.uniride_iam_service.application.dtos.request.UpdateDriverProfileRequest;
import com.campusdrive.uniride_iam_service.application.dtos.request.UpdateStudentProfileRequest;
import com.campusdrive.uniride_iam_service.application.dtos.response.UserProfileResponse;
import com.campusdrive.uniride_iam_service.application.dtos.response.VehicleResponse;
import com.campusdrive.uniride_iam_service.application.services.UserService;
import com.campusdrive.uniride_iam_service.domain.models.Driver;
import com.campusdrive.uniride_iam_service.domain.models.Role;
import com.campusdrive.uniride_iam_service.domain.models.User;
import com.campusdrive.uniride_iam_service.domain.repositories.DriverRepository;
import com.campusdrive.uniride_iam_service.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser() {
        String userIdStr = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(userIdStr);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Identify role (STUDENT or DRIVER) from user's roles
        String role = user.getRoles() != null && !user.getRoles().isEmpty()
                ? user.getRoles().iterator().next().name()
                : null;

        UserProfileResponse.UserProfileResponseBuilder responseBuilder = UserProfileResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(role);

        if (user.getRoles() != null && user.getRoles().contains(Role.DRIVER)) {
            driverRepository.findByUserId(userId).ifPresent(driver -> {
                responseBuilder.cardNumber(driver.getCardNumber());
                if (driver.getVehicle() != null) {
                    responseBuilder.vehicle(VehicleResponse.builder()
                            .type(driver.getVehicle().getType())
                            .name(driver.getVehicle().getName())
                            .licenseNumber(driver.getLicenseNumber())
                            .build());
                }
            });
        }

        return ResponseEntity.ok(responseBuilder.build());
    }

    @PutMapping("/me/student")
    public ResponseEntity<UserProfileResponse> updateStudentProfile(@RequestBody UpdateStudentProfileRequest request) {
        String userIdStr = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(userIdStr);
        
        UserProfileResponse updatedProfile = userService.updateStudentProfile(userId, request);
        return ResponseEntity.ok(updatedProfile);
    }

    @PutMapping("/me/driver")
    public ResponseEntity<UserProfileResponse> updateDriverProfile(@RequestBody UpdateDriverProfileRequest request) {
        String userIdStr = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(userIdStr);
        
        UserProfileResponse updatedProfile = userService.updateDriverProfile(userId, request);
        return ResponseEntity.ok(updatedProfile);
    }
}
