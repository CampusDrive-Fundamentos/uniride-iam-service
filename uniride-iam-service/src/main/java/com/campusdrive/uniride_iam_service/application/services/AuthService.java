package com.campusdrive.uniride_iam_service.application.services;

import com.campusdrive.uniride_iam_service.domain.repositories.StudentRepository;
import com.campusdrive.uniride_iam_service.application.dtos.request.DriverSignUpRequest;
import com.campusdrive.uniride_iam_service.application.dtos.request.LoginRequest;
import com.campusdrive.uniride_iam_service.application.dtos.request.StudentSignUpRequest;
import com.campusdrive.uniride_iam_service.application.dtos.response.AuthResponse;
import com.campusdrive.uniride_iam_service.domain.exceptions.InvalidDriverCredentialsException;
import com.campusdrive.uniride_iam_service.domain.exceptions.InvalidEmailException;
import com.campusdrive.uniride_iam_service.domain.exceptions.UserAlreadyExistsException;
import com.campusdrive.uniride_iam_service.domain.models.Driver;
import com.campusdrive.uniride_iam_service.domain.models.Role;
import com.campusdrive.uniride_iam_service.domain.models.Student;
import com.campusdrive.uniride_iam_service.domain.models.User;
import com.campusdrive.uniride_iam_service.domain.patterns.SecurityValidator;
import com.campusdrive.uniride_iam_service.domain.repositories.DriverRepository;
import com.campusdrive.uniride_iam_service.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final DriverRepository driverRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse signUpStudent(StudentSignUpRequest request) {
        // 1. Validar correo .edu y TIU usando el Singleton
        if (!SecurityValidator.getInstance().isAcademicEmailValid(request.getEmail())) {
            throw new InvalidEmailException("Email must be a valid .edu or .edu.pe address");
        }

        if (!SecurityValidator.getInstance().isTiuValid(request.getTiuPhoto())) {
            throw new InvalidEmailException("TIU photo is invalid or missing");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .roles(Set.of(Role.STUDENT))
                .build();

        user = userRepository.save(user);

        Student student = Student.builder()
                .universityName(request.getUniversityName())
                .tiuPhoto(request.getTiuPhoto())
                .user(user)
                .build();

        studentRepository.save(student);

        String token = tokenService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public AuthResponse signUpDriver(DriverSignUpRequest request) {
        if (!SecurityValidator.getInstance().isEmailValid(request.getEmail())) {
            throw new InvalidEmailException("Email must be a valid format (e.g. user@domain.com)");
        }

        if (!SecurityValidator.getInstance().hasCleanCriminalRecords(request.getDni(), request.getCulCertificate())) {
            throw new InvalidDriverCredentialsException(
                    "Registro rechazado: La validación de antecedentes (CUL) indica que el conductor no cumple con los requisitos de seguridad de UniRide. Debes de colocar un CUL válido (CUL-VALIDO-100)");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        // 4. Crear usuario base
        User user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .roles(Set.of(Role.DRIVER))
                .build();

        user = userRepository.save(user);

        Driver driver = Driver.builder()
                .dni(request.getDni())
                .licenseNumber(request.getLicenseNumber())
                .culCertificate(request.getCulCertificate())
                .isActive(true)
                .user(user)
                .build();

        driverRepository.save(driver);

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
