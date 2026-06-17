package com.campusdrive.uniride_iam_service.infrastructure.persistence.repositories;

import com.campusdrive.uniride_iam_service.infrastructure.persistence.entities.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaDriverRepository extends JpaRepository<DriverEntity, Long> {
    boolean existsByDni(String dni);
    boolean existsByLicenseNumber(String licenseNumber);
    Optional<DriverEntity> findByUserId(Long userId);
}

