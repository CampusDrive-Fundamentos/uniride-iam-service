package com.campusdrive.uniride_iam_service.application.services;

import com.campusdrive.uniride_iam_service.application.dtos.request.UpdateDriverProfileRequest;
import com.campusdrive.uniride_iam_service.application.dtos.request.UpdateStudentProfileRequest;
import com.campusdrive.uniride_iam_service.application.dtos.response.UserProfileResponse;
import com.campusdrive.uniride_iam_service.application.dtos.response.VehicleResponse;
import com.campusdrive.uniride_iam_service.domain.models.Driver;
import com.campusdrive.uniride_iam_service.domain.models.Role;
import com.campusdrive.uniride_iam_service.domain.models.User;
import com.campusdrive.uniride_iam_service.domain.models.Vehicle;
import com.campusdrive.uniride_iam_service.domain.repositories.DriverRepository;
import com.campusdrive.uniride_iam_service.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    @Transactional
    public UserProfileResponse updateStudentProfile(Long userId, UpdateStudentProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRoles().contains(Role.STUDENT)) {
            throw new RuntimeException("User is not a student");
        }

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            user.setLastName(request.getLastName());
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        user = userRepository.save(user);

        String roleStr = user.getRoles() != null && !user.getRoles().isEmpty()
                ? user.getRoles().iterator().next().name()
                : null;

        return UserProfileResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(roleStr)
                .build();
    }

    @Transactional
    public UserProfileResponse updateDriverProfile(Long userId, UpdateDriverProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRoles().contains(Role.DRIVER)) {
            throw new RuntimeException("User is not a driver");
        }

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            user.setLastName(request.getLastName());
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        user = userRepository.save(user);

        Driver driver = driverRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Driver profile not found"));

        if (request.getCardNumber() != null) {
            driver.setCardNumber(request.getCardNumber());
        }

        if (request.getVehicle() != null) {
            Vehicle vehicle = driver.getVehicle();
            if (vehicle == null) {
                vehicle = new Vehicle();
            }
            if (request.getVehicle().getType() != null) {
                vehicle.setType(request.getVehicle().getType());
            }
            if (request.getVehicle().getName() != null) {
                vehicle.setName(request.getVehicle().getName());
            }
            driver.setVehicle(vehicle);

            if (request.getVehicle().getLicenseNumber() != null && !request.getVehicle().getLicenseNumber().isBlank()) {
                driver.setLicenseNumber(request.getVehicle().getLicenseNumber());
            }
        }

        driver = driverRepository.save(driver);

        String roleStr = user.getRoles() != null && !user.getRoles().isEmpty()
                ? user.getRoles().iterator().next().name()
                : null;

        UserProfileResponse.UserProfileResponseBuilder responseBuilder = UserProfileResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(roleStr)
                .cardNumber(driver.getCardNumber());

        if (driver.getVehicle() != null) {
            responseBuilder.vehicle(VehicleResponse.builder()
                    .type(driver.getVehicle().getType())
                    .name(driver.getVehicle().getName())
                    .licenseNumber(driver.getLicenseNumber())
                    .build());
        }

        return responseBuilder.build();
    }
}
