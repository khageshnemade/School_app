package space.khagesh.school.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import space.khagesh.school.dto.StudentDTO;
import space.khagesh.school.service.StudentService;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Get student by ID", description = "Fetch student details by ID along with subject IDs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully fetched student details"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> get(@PathVariable String id) {  // Change UUID to String
        return ResponseEntity.ok(studentService.getById(id));
    }

    @Operation(summary = "Get all students", description = "Fetch details of all students")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully fetched all students")
    })
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @Operation(summary = "Update student details", description = "Update the name and subjects of an existing student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated student details"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable String id, @RequestBody StudentDTO studentDTO) {  // Change UUID to String
        return ResponseEntity.ok(studentService.update(id, studentDTO));
    }

    @Operation(summary = "Delete student", description = "Delete a student by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted student"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {  // Change UUID to String
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add a subject to a student", description = "Associate a subject to a student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully added subject to student"),
        @ApiResponse(responseCode = "404", description = "Student or Subject not found")
    })
    @PostMapping("/{studentId}/subjects/{subjectId}")
    public ResponseEntity<StudentDTO> addSubject(
        @PathVariable String studentId, @PathVariable String subjectId) {  // Change UUID to String
        return ResponseEntity.ok(studentService.addSubject(studentId, subjectId));
    }

    @Operation(summary = "Remove a subject from a student", description = "Dissociate a subject from a student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully removed subject from student"),
        @ApiResponse(responseCode = "404", description = "Student or Subject not found")
    })
    @DeleteMapping("/{studentId}/subjects/{subjectId}")
    public ResponseEntity<StudentDTO> removeSubject(
        @PathVariable String studentId, @PathVariable String subjectId) {  // Change UUID to String
        return ResponseEntity.ok(studentService.removeSubject(studentId, subjectId));
    }
}
