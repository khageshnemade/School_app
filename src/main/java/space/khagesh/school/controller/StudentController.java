package space.khagesh.school.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import space.khagesh.school.entity.Student;
import space.khagesh.school.service.StudentService;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class StudentController {
    private final StudentService service;

    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student s) {
        return ResponseEntity.ok(service.create(s));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable UUID id, @RequestBody Student s) {
        return ResponseEntity.ok(service.update(id, s));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/subjects/{subjectId}")
    public ResponseEntity<Student> addSubject(@PathVariable UUID studentId, @PathVariable UUID subjectId) {
        return ResponseEntity.ok(service.addSubject(studentId, subjectId));
    }

    @DeleteMapping("/{studentId}/subjects/{subjectId}")
    public ResponseEntity<Student> removeSubject(@PathVariable UUID studentId, @PathVariable UUID subjectId) {
        return ResponseEntity.ok(service.removeSubject(studentId, subjectId));
    }
}
