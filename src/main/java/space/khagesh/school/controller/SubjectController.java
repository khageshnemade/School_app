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
import lombok.RequiredArgsConstructor;
import space.khagesh.school.entity.Subject;
import space.khagesh.school.service.SubjectService;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class SubjectController {

    private final SubjectService service;

    @PostMapping
    public ResponseEntity<Subject> create(@RequestBody Subject s) {
        return ResponseEntity.ok(service.create(s));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> update(@PathVariable UUID id, @RequestBody Subject s) {
        return ResponseEntity.ok(service.update(id, s));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Relations with Teachers ----
    @PostMapping("/{subjectId}/teachers/{teacherId}")
    public ResponseEntity<Subject> addTeacher(@PathVariable UUID subjectId, @PathVariable UUID teacherId) {
        return ResponseEntity.ok(service.addTeacher(subjectId, teacherId));
    }

    @DeleteMapping("/{subjectId}/teachers/{teacherId}")
    public ResponseEntity<Subject> removeTeacher(@PathVariable UUID subjectId, @PathVariable UUID teacherId) {
        return ResponseEntity.ok(service.removeTeacher(subjectId, teacherId));
    }

    // ---- Relations with Students ----
    @PostMapping("/{subjectId}/students/{studentId}")
    public ResponseEntity<Subject> addStudent(@PathVariable UUID subjectId, @PathVariable UUID studentId) {
        return ResponseEntity.ok(service.addStudent(subjectId, studentId));
    }

    @DeleteMapping("/{subjectId}/students/{studentId}")
    public ResponseEntity<Subject> removeStudent(@PathVariable UUID subjectId, @PathVariable UUID studentId) {
        return ResponseEntity.ok(service.removeStudent(subjectId, studentId));
    }
}
