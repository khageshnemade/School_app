package space.khagesh.school.service;

import java.util.List;

import space.khagesh.school.dto.TeacherDTO;

public interface TeacherService {
    TeacherDTO getById(String id);
    List<TeacherDTO> getAll();
    TeacherDTO update(String id, TeacherDTO t);
    void delete(String id);

    TeacherDTO addSubject(String teacherId, String subjectId);
    TeacherDTO removeSubject(String teacherId, String subjectId);
}
