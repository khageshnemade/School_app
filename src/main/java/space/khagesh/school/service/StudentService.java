package space.khagesh.school.service;

import java.util.List;

import space.khagesh.school.dto.StudentDTO;

public interface StudentService {
    StudentDTO getById(String id);
    List<StudentDTO> getAll();
    StudentDTO update(String id, StudentDTO studentDTO);
    void delete(String id);

    StudentDTO addSubject(String studentId, String subjectId);
    StudentDTO removeSubject(String studentId, String subjectId);
}
