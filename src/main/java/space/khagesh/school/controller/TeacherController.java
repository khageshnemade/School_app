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
import space.khagesh.school.entity.Teacher;
import space.khagesh.school.service.TeacherService;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class TeacherController {

	private final TeacherService service;

	@PostMapping
	public ResponseEntity<Teacher> create(@RequestBody Teacher t) {
		return ResponseEntity.ok(service.create(t));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Teacher> get(@PathVariable UUID id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@GetMapping
	public ResponseEntity<List<Teacher>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Teacher> update(@PathVariable UUID id, @RequestBody Teacher t) {
		return ResponseEntity.ok(service.update(id, t));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{teacherId}/subjects/{subjectId}")
	public ResponseEntity<Teacher> addSubject(@PathVariable UUID teacherId, @PathVariable UUID subjectId) {
		return ResponseEntity.ok(service.addSubject(teacherId, subjectId));
	}

	@DeleteMapping("/{teacherId}/subjects/{subjectId}")
	public ResponseEntity<Teacher> removeSubject(@PathVariable UUID teacherId, @PathVariable UUID subjectId) {
		return ResponseEntity.ok(service.removeSubject(teacherId, subjectId));
	}
}
