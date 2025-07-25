package space.khagesh.school.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import space.khagesh.school.dto.TeacherDTO;
import space.khagesh.school.service.TeacherService;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class TeacherController {

    private final TeacherService service;

    @Operation(summary = "Get a teacher by ID", description = "Retrieve a teacher by their unique ID.")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Teacher found"),
        @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    public ResponseEntity<TeacherDTO> get(
            @Parameter(description = "ID of the teacher to be retrieved", required = true)
            @PathVariable String id) {  // Change UUID to String
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Get all teachers", description = "Retrieve a list of all teachers.")
    @GetMapping
    @ApiResponse(responseCode = "200", description = "List of all teachers")
    public ResponseEntity<List<TeacherDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Update a teacher by ID", description = "Update the details of an existing teacher.")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Teacher successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    public ResponseEntity<TeacherDTO> update(
            @Parameter(description = "ID of the teacher to be updated", required = true)
            @PathVariable String id,  // Change UUID to String
            @RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok(service.update(id, teacherDTO));
    }

    @Operation(summary = "Delete a teacher by ID", description = "Delete the teacher with the specified ID.")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Teacher successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the teacher to be deleted", required = true)
            @PathVariable String id) {  // Change UUID to String
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add a subject to a teacher", description = "Associate a subject with the teacher.")
    @PostMapping("/{teacherId}/subjects/{subjectId}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subject successfully added to teacher"),
        @ApiResponse(responseCode = "404", description = "Teacher or Subject not found")
    })
    public ResponseEntity<TeacherDTO> addSubject(
            @Parameter(description = "Teacher ID", required = true)
            @PathVariable String teacherId,  // Change UUID to String
            @Parameter(description = "Subject ID", required = true)
            @PathVariable String subjectId) {  // Change UUID to String
        return ResponseEntity.ok(service.addSubject(teacherId, subjectId));
    }

    @Operation(summary = "Remove a subject from a teacher", description = "Remove a subject from the teacher.")
    @DeleteMapping("/{teacherId}/subjects/{subjectId}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subject successfully removed from teacher"),
        @ApiResponse(responseCode = "404", description = "Teacher or Subject not found")
    })
    public ResponseEntity<TeacherDTO> removeSubject(
            @Parameter(description = "Teacher ID", required = true)
            @PathVariable String teacherId,  // Change UUID to String
            @Parameter(description = "Subject ID", required = true)
            @PathVariable String subjectId) {  // Change UUID to String
        return ResponseEntity.ok(service.removeSubject(teacherId, subjectId));
    }
}
