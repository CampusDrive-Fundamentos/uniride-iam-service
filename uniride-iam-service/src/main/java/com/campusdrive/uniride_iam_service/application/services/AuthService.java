package com.campusdrive.uniride_iam_service.application.services;

import com.campusdrive.uniride_iam_service.application.dtos.request.LoginRequest;
import com.campusdrive.uniride_iam_service.application.dtos.request.StudentSignUpRequest;
import com.campusdrive.uniride_iam_service.application.dtos.response.AuthResponse;
import com.campusdrive.uniride_iam_service.domain.exceptions.InvalidEmailException;
import com.campusdrive.uniride_iam_service.domain.exceptions.UserAlreadyExistsException;
import com.campusdrive.uniride_iam_service.domain.models.Role;
import com.campusdrive.uniride_iam_service.domain.models.User;
import com.campusdrive.uniride_iam_service.domain.patterns.SecurityValidator;
import com.campusdrive.uniride_iam_service.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse signUp(StudentSignUpRequest request) {
        // 1. Validar correo .edu usando el Singleton
        if (!SecurityValidator.getInstance().isEmailValid(request.getEmail())) {
            throw new InvalidEmailException("Email must be a valid .edu or .edu.pe address");
        }

        // 2. Verificar si ya existe
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        // 3. Crear usuario
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.STUDENT))
                .build();

        userRepository.save(user);

        // 4. Generar Token
        String token = tokenService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public AuthResponse signIn(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = tokenService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
