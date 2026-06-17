package com.campusdrive.uniride_iam_service.domain.repositories;

import com.campusdrive.uniride_iam_service.domain.models.Driver;
import java.util.Optional;

public interface DriverRepository {
    Driver save(Driver driver);
    boolean existsByDni(String dni);
    boolean existsByLicenseNumber(String licenseNumber);
    Optional<Driver> findByUserId(Long userId);
}

