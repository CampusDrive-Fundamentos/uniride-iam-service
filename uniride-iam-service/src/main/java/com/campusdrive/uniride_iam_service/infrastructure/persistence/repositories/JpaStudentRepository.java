package com.campusdrive.uniride_iam_service.infrastructure.persistence.repositories;

import com.campusdrive.uniride_iam_service.infrastructure.persistence.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStudentRepository extends JpaRepository<StudentEntity, Long> {
}
