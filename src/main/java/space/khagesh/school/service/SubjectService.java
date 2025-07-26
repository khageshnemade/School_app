package space.khagesh.school.service;

import java.util.List;

import space.khagesh.school.api.ApiResponse;
import space.khagesh.school.dto.SubjectDTO;

public interface SubjectService {
	ApiResponse<SubjectDTO> create(SubjectDTO s);
    ApiResponse<SubjectDTO> getById(String id);
    ApiResponse<List<SubjectDTO>> getAll();
    ApiResponse<SubjectDTO> update(String id, SubjectDTO s);
    ApiResponse<String> delete(String id);
}
