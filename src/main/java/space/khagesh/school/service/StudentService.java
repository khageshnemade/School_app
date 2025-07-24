package space.khagesh.school.service;
import java.util.List;
import java.util.UUID;

import space.khagesh.school.entity.Student;

public interface StudentService {
    Student create(Student s);
    Student getById(UUID id);
    List<Student> getAll();
    Student update(UUID id, Student s);
    void delete(UUID id);

    Student addSubject(UUID studentId, UUID subjectId);
    Student removeSubject(UUID studentId, UUID subjectId);
}
