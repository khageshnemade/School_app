package space.khagesh.school.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import space.khagesh.school.api.ApiResponse;
import space.khagesh.school.dto.LoginRequest;
import space.khagesh.school.dto.RegisterRequest;
import space.khagesh.school.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new student")
    @PostMapping("/signup/student")
    public ResponseEntity<space.khagesh.school.api.ApiResponse<String>> registerStudent(@RequestBody RegisterRequest req) {
        String message = authService.registerStudent(req.getUsername(), req.getPassword(), req.getName());
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .build());
    }

    @Operation(summary = "Register a new teacher")
    @PostMapping("/signup/teacher")
    public ResponseEntity<ApiResponse<String>> registerTeacher(@RequestBody RegisterRequest req) {
        String message = authService.registerTeacher(req.getUsername(), req.getPassword(), req.getName());
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody LoginRequest req) {
        Map<String, String> tokenData = authService.login(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(ApiResponse.<Map<String, String>>builder()
                .success(true)
                .message("Login successful")
                .data(tokenData)
                .build());
    }

}
