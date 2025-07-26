package space.khagesh.school.service;

import java.util.List;

import space.khagesh.school.api.ApiResponse;
import space.khagesh.school.dto.StudentDTO;

public interface StudentService {
	 ApiResponse<StudentDTO> getById(String id);
	    ApiResponse<List<StudentDTO>> getAll();
	    ApiResponse<StudentDTO> update(String id, StudentDTO studentDTO);
	    ApiResponse<String> delete(String id);
	    ApiResponse<StudentDTO> addSubject(String studentId, String subjectId);
	    ApiResponse<StudentDTO> removeSubject(String studentId, String subjectId);
}
