package com.campusdrive.uniride_iam_service.application.services;

import com.campusdrive.uniride_iam_service.domain.models.User;

public interface TokenService {
    String generateToken(User user);
    String getSubjectFromToken(String token);
    boolean validateToken(String token);
}
