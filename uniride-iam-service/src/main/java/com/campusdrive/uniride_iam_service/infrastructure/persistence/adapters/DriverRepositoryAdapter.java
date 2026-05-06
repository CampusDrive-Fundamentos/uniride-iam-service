package com.campusdrive.uniride_iam_service.infrastructure.persistence.adapters;

import com.campusdrive.uniride_iam_service.domain.models.Driver;
import com.campusdrive.uniride_iam_service.domain.repositories.DriverRepository;
import com.campusdrive.uniride_iam_service.infrastructure.persistence.entities.DriverEntity;
import com.campusdrive.uniride_iam_service.infrastructure.persistence.entities.UserEntity;
import com.campusdrive.uniride_iam_service.infrastructure.persistence.repositories.JpaDriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverRepositoryAdapter implements DriverRepository {
    private final JpaDriverRepository jpaDriverRepository;

    @Override
    public Driver save(Driver driver) {
        DriverEntity entity = DriverEntity.builder()
                .id(driver.getId())
                .dni(driver.getDni())
                .licenseNumber(driver.getLicenseNumber())
                .culCertificate(driver.getCulCertificate())
                .isActive(driver.isActive())
                .user(UserEntity.builder().id(driver.getUser().getId()).build())
                .build();
        
        DriverEntity savedEntity = jpaDriverRepository.save(entity);
        driver.setId(savedEntity.getId());
        return driver;
    }
}
