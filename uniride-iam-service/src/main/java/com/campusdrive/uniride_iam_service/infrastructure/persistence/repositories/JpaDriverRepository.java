package com.campusdrive.uniride_iam_service.infrastructure.persistence.repositories;

import com.campusdrive.uniride_iam_service.infrastructure.persistence.entities.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDriverRepository extends JpaRepository<DriverEntity, Long> {
}
