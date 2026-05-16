package com.campusdrive.uniride_iam_service.application.services;

import com.campusdrive.uniride_iam_service.application.dtos.request.LoginRequest;
import com.campusdrive.uniride_iam_service.application.dtos.request.StudentSignUpRequest;
import com.campusdrive.uniride_iam_service.application.dtos.response.AuthResponse;
import com.campusdrive.uniride_iam_service.domain.exceptions.UserAlreadyExistsException;
import com.campusdrive.uniride_iam_service.domain.models.Role;
import com.campusdrive.uniride_iam_service.domain.models.User;
import com.campusdrive.uniride_iam_service.domain.repositories.DriverRepository;
import com.campusdrive.uniride_iam_service.domain.repositories.StudentRepository;
import com.campusdrive.uniride_iam_service.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private StudentSignUpRequest studentRequest;
    private User testUser;

    @BeforeEach
    void setUp() {
        studentRequest = new StudentSignUpRequest();
        studentRequest.setUsername("johndoe");
        studentRequest.setEmail("john.doe@university.edu");
        studentRequest.setPassword("password123");
        studentRequest.setFirstName("John");
        studentRequest.setLastName("Doe");
        studentRequest.setPhoneNumber("987654321");
        studentRequest.setUniversityName("UPC");
        studentRequest.setTiuPhoto("tiu-photo-url");

        testUser = User.builder()
                .id(1L)
                .username("johndoe")
                .email("john.doe@university.edu")
                .password("encodedPassword")
                .roles(Set.of(Role.STUDENT))
                .build();
    }

    @Test
    @DisplayName("Should sign up student successfully when data is valid")
    void signUpStudent_Successful() {
        // Arrange
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(testUser);
        when(tokenService.generateToken(any())).thenReturn("mock-jwt-token");

        // Act
        AuthResponse response = authService.signUpStudent(studentRequest);

        // Assert
        assertNotNull(response);
        assertEquals("mock-jwt-token", response.getToken());
        assertEquals("johndoe", response.getUsername());
        verify(userRepository, times(1)).save(any());
        verify(studentRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw exception when student email already exists")
    void signUpStudent_ThrowsUserAlreadyExistsException() {
        // Arrange
        when(userRepository.findByEmail(studentRequest.getEmail())).thenReturn(Optional.of(testUser));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> {
            authService.signUpStudent(studentRequest);
        });

        verify(userRepository, never()).save(any());
        verify(studentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should sign in successfully when credentials are correct")
    void signIn_Successful() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("johndoe");
        loginRequest.setPassword("password123");

        when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(tokenService.generateToken(testUser)).thenReturn("mock-jwt-token");

        // Act
        AuthResponse response = authService.signIn(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("mock-jwt-token", response.getToken());
        assertEquals("johndoe", response.getUsername());
        verify(tokenService, times(1)).generateToken(testUser);
    }
}
