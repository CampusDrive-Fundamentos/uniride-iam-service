package com.campusdrive.uniride_iam_service.presentation.controller;

import com.campusdrive.uniride_iam_service.application.dtos.request.DriverSignUpRequest;
import com.campusdrive.uniride_iam_service.application.dtos.request.LoginRequest;
import com.campusdrive.uniride_iam_service.application.dtos.request.StudentSignUpRequest;
import com.campusdrive.uniride_iam_service.application.dtos.response.AuthResponse;
import com.campusdrive.uniride_iam_service.application.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup/student")
    public ResponseEntity<AuthResponse> signUpStudent(@Valid @RequestBody StudentSignUpRequest request) {
        return ResponseEntity.ok(authService.signUpStudent(request));
    }

    @PostMapping("/signup/driver")
    public ResponseEntity<AuthResponse> signUpDriver(@Valid @RequestBody DriverSignUpRequest request) {
        return ResponseEntity.ok(authService.signUpDriver(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }
}
