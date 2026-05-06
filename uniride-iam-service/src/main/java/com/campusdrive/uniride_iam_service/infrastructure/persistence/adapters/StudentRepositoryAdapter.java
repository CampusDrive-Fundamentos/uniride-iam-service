package com.campusdrive.uniride_iam_service.infrastructure.persistence.adapters;

import com.campusdrive.uniride_iam_service.domain.models.Student;
import com.campusdrive.uniride_iam_service.domain.repositories.StudentRepository;
import com.campusdrive.uniride_iam_service.infrastructure.persistence.entities.StudentEntity;
import com.campusdrive.uniride_iam_service.infrastructure.persistence.entities.UserEntity;
import com.campusdrive.uniride_iam_service.infrastructure.persistence.repositories.JpaStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentRepositoryAdapter implements StudentRepository {
    private final JpaStudentRepository jpaStudentRepository;
    private final com.campusdrive.uniride_iam_service.infrastructure.persistence.repositories.JpaUserRepository jpaUserRepository;

    @Override
    public Student save(Student student) {
        UserEntity userEntity = jpaUserRepository.findById(student.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudentEntity entity = StudentEntity.builder()
                .id(student.getId())
                .studentCode(student.getStudentCode())
                .universityName(student.getUniversityName())
                .tiuPhoto(student.getTiuPhoto())
                .user(userEntity)
                .build();
        
        StudentEntity savedEntity = jpaStudentRepository.save(entity);
        student.setId(savedEntity.getId());
        return student;
    }
}
