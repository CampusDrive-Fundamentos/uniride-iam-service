package com.campusdrive.uniride_iam_service.domain.repositories;

import com.campusdrive.uniride_iam_service.domain.models.Student;

public interface StudentRepository {
    Student save(Student student);
}
