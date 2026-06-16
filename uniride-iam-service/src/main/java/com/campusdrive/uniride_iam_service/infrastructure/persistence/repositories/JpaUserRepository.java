package com.campusdrive.uniride_iam_service.infrastructure.persistence.repositories;

import com.campusdrive.uniride_iam_service.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
