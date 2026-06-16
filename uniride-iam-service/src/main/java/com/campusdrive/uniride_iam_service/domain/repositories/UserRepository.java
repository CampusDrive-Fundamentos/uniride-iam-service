package com.campusdrive.uniride_iam_service.domain.repositories;

import com.campusdrive.uniride_iam_service.domain.models.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
