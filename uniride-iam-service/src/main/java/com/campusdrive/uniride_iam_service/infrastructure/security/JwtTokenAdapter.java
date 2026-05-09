package com.campusdrive.uniride_iam_service.infrastructure.security;

import com.campusdrive.uniride_iam_service.application.services.TokenService;
import com.campusdrive.uniride_iam_service.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenAdapter implements TokenService {
    private final JwtProvider jwtProvider;

    @Override
    public String generateToken(User user) {
        return jwtProvider.generateToken(user.getId().toString());
    }

    @Override
    public String getSubjectFromToken(String token) {
        return jwtProvider.getSubjectFromToken(token);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtProvider.validateToken(token);
    }
}
