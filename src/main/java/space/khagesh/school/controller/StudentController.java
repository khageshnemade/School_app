package space.khagesh.school.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import space.khagesh.school.api.ApiResponse;
import space.khagesh.school.dto.StudentDTO;
import space.khagesh.school.service.StudentService;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Get student by ID", description = "Fetch student details by ID along with subject IDs")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> get(@PathVariable String id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @Operation(summary = "Get all students", description = "Fetch details of all students")
 
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @Operation(summary = "Update student details", description = "Update the name and subjects of an existing student")

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> update(
            @PathVariable String id,
            @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.update(id, studentDTO));
    }

    @Operation(summary = "Delete student", description = "Delete a student by their ID")

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String id) {
        return ResponseEntity.ok(studentService.delete(id));
    }

    @Operation(summary = "Add a subject to a student", description = "Associate a subject to a student")
 
    @PostMapping("/{studentId}/subjects/{subjectId}")
    public ResponseEntity<ApiResponse<StudentDTO>> addSubject(
            @PathVariable String studentId,
            @PathVariable String subjectId) {
        return ResponseEntity.ok(studentService.addSubject(studentId, subjectId));
    }

    @Operation(summary = "Remove a subject from a student", description = "Dissociate a subject from a student")
    @DeleteMapping("/{studentId}/subjects/{subjectId}")
    public ResponseEntity<ApiResponse<StudentDTO>> removeSubject(
            @PathVariable String studentId,
            @PathVariable String subjectId) {
        return ResponseEntity.ok(studentService.removeSubject(studentId, subjectId));
    }
}
