package space.khagesh.school.service;



import java.util.List;
import java.util.UUID;

import space.khagesh.school.entity.Subject;

public interface SubjectService {
	Subject create(Subject s);

	Subject getById(UUID id);

	List<Subject> getAll();

	Subject update(UUID id, Subject s);

	void delete(UUID id);

	Subject addTeacher(UUID subjectId, UUID teacherId);

	Subject removeTeacher(UUID subjectId, UUID teacherId);

	Subject addStudent(UUID subjectId, UUID studentId);

	Subject removeStudent(UUID subjectId, UUID studentId);
}
