package space.khagesh.school.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import space.khagesh.school.api.ApiResponse;
import space.khagesh.school.dto.SubjectDTO;
import space.khagesh.school.service.SubjectService;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class SubjectController {

    private final SubjectService service;

    @Operation(summary = "Create a new subject", description = "Creates a new subject with the provided details.")
    @PostMapping
    public ResponseEntity<ApiResponse<SubjectDTO>> create(@RequestBody SubjectDTO subjectDTO) {
        return ResponseEntity.ok(service.create(subjectDTO));
    }

    @Operation(summary = "Get a subject by ID", description = "Retrieve a subject by its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectDTO>> get(
            @Parameter(description = "ID of the subject to be retrieved", required = true)
            @PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Get all subjects", description = "Retrieve a list of all subjects.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Update a subject by ID", description = "Update the details of an existing subject.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectDTO>> update(
            @Parameter(description = "ID of the subject to be updated", required = true)
            @PathVariable String id,
            @RequestBody SubjectDTO subjectDTO) {
        return ResponseEntity.ok(service.update(id, subjectDTO));
    }

    @Operation(summary = "Delete a subject by ID", description = "Delete the subject with the specified ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(
            @Parameter(description = "ID of the subject to be deleted", required = true)
            @PathVariable String id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
