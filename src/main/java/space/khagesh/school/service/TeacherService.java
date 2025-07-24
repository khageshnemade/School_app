package space.khagesh.school.service;

import java.util.List;
import java.util.UUID;

import space.khagesh.school.entity.Teacher;

public interface TeacherService {
    Teacher create(Teacher t);
    Teacher getById(UUID id);
    List<Teacher> getAll();
    Teacher update(UUID id, Teacher t);
    void delete(UUID id);

    Teacher addSubject(UUID teacherId, UUID subjectId);
    Teacher removeSubject(UUID teacherId, UUID subjectId);
}
