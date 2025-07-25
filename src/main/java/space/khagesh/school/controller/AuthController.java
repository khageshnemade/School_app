package space.khagesh.school.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import space.khagesh.school.dto.LoginRequest;
import space.khagesh.school.dto.RegisterRequest;
import space.khagesh.school.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new student", description = "Endpoint to register a new student with username, password, and name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully registered student"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/signup/student")
    public ResponseEntity<String> registerStudent(
        @Parameter(description = "Student registration details") @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.registerStudent(req.getUsername(), req.getPassword(), req.getName()));
    }

    @Operation(summary = "Register a new teacher", description = "Endpoint to register a new teacher with username, password, and name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully registered teacher"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/signup/teacher")
    public ResponseEntity<String> registerTeacher(
        @Parameter(description = "Teacher registration details") @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.registerTeacher(req.getUsername(), req.getPassword(), req.getName()));
    }

    @Operation(summary = "Login", description = "Endpoint to log in with username and password and receive a token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully logged in and received token"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
        @Parameter(description = "Login request with username and password") @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req.getUsername(), req.getPassword()));
    }
}
