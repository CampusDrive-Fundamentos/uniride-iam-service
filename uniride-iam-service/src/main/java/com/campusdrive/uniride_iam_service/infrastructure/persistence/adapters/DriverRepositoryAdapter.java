package com.campusdrive.uniride_iam_service.infrastructure.persistence.adapters;

import com.campusdrive.uniride_iam_service.domain.models.Driver;
import com.campusdrive.uniride_iam_service.domain.repositories.DriverRepository;
import com.campusdrive.uniride_iam_service.infrastructure.persistence.entities.DriverEntity;
import com.campusdrive.uniride_iam_service.infrastructure.persistence.entities.UserEntity;
import com.campusdrive.uniride_iam_service.infrastructure.persistence.repositories.JpaDriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DriverRepositoryAdapter implements DriverRepository {
    private final JpaDriverRepository jpaDriverRepository;
    private final com.campusdrive.uniride_iam_service.infrastructure.persistence.repositories.JpaUserRepository jpaUserRepository;

    @Override
    public Driver save(Driver driver) {
        UserEntity userEntity = jpaUserRepository.findById(driver.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DriverEntity entity = DriverEntity.builder()
                .id(driver.getId())
                .dni(driver.getDni())
                .licenseNumber(driver.getLicenseNumber())
                .culCertificate(driver.getCulCertificate())
                .isActive(driver.isActive())
                .cardNumber(driver.getCardNumber())
                .vehicleType(driver.getVehicle() != null ? driver.getVehicle().getType() : null)
                .vehicleName(driver.getVehicle() != null ? driver.getVehicle().getName() : null)
                .user(userEntity)
                .build();
        
        DriverEntity savedEntity = jpaDriverRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public boolean existsByDni(String dni) {
        return jpaDriverRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        return jpaDriverRepository.existsByLicenseNumber(licenseNumber);
    }

    @Override
    public Optional<Driver> findByUserId(Long userId) {
        return jpaDriverRepository.findByUserId(userId).map(this::toDomain);
    }

    private Driver toDomain(DriverEntity entity) {
        if (entity == null) return null;
        
        com.campusdrive.uniride_iam_service.domain.models.User user = com.campusdrive.uniride_iam_service.domain.models.User.builder()
                .id(entity.getUser().getId())
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .email(entity.getUser().getEmail())
                .password(entity.getUser().getPassword())
                .phoneNumber(entity.getUser().getPhoneNumber())
                .roles(entity.getUser().getRoles())
                .build();
                
        com.campusdrive.uniride_iam_service.domain.models.Vehicle vehicle = null;
        if (entity.getVehicleType() != null || entity.getVehicleName() != null) {
            vehicle = com.campusdrive.uniride_iam_service.domain.models.Vehicle.builder()
                    .type(entity.getVehicleType())
                    .name(entity.getVehicleName())
                    .build();
        }

        return Driver.builder()
                .id(entity.getId())
                .dni(entity.getDni())
                .licenseNumber(entity.getLicenseNumber())
                .culCertificate(entity.getCulCertificate())
                .isActive(entity.isActive())
                .cardNumber(entity.getCardNumber())
                .vehicle(vehicle)
                .user(user)
                .build();
    }
}
