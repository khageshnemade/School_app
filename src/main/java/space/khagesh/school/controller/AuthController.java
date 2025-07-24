package space.khagesh.school.controller;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import space.khagesh.school.dto.LoginRequest;
import space.khagesh.school.dto.RegisterRequest;
import space.khagesh.school.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup/student")
    public ResponseEntity<String> registerStudent(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.registerStudent(req.getUsername(), req.getPassword(), req.getName()));
    }

    @PostMapping("/signup/teacher")
    public ResponseEntity<String> registerTeacher(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.registerTeacher(req.getUsername(), req.getPassword(), req.getName()));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req.getUsername(), req.getPassword()));
    }
}





