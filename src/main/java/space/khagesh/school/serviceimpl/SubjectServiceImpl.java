package space.khagesh.school.serviceimpl;


import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.khagesh.school.entity.Student;
import space.khagesh.school.entity.Subject;
import space.khagesh.school.entity.Teacher;
import space.khagesh.school.repository.StudentRepository;
import space.khagesh.school.repository.SubjectRepository;
import space.khagesh.school.repository.TeacherRepository;
import space.khagesh.school.service.SubjectService;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public Subject create(Subject s) {
        return subjectRepository.save(s);
    }

    @Override
    public Subject getById(UUID id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
    }

    @Override
    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject update(UUID id, Subject s) {
        Subject existing = getById(id);
        existing.setName(s.getName());
        // students/teachers sets को यहाँ override नहीं कर रहे (चाहें तो कर सकते हैं)
        return subjectRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public Subject addTeacher(UUID subjectId, UUID teacherId) {
        Subject subject = getById(subjectId);
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        subject.getTeachers().add(teacher);
        return subjectRepository.save(subject);
    }

    @Override
    public Subject removeTeacher(UUID subjectId, UUID teacherId) {
        Subject subject = getById(subjectId);
        subject.getTeachers().removeIf(t -> t.getId().equals(teacherId));
        return subjectRepository.save(subject);
    }

    @Override
    public Subject addStudent(UUID subjectId, UUID studentId) {
        Subject subject = getById(subjectId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        subject.getStudents().add(student);
        return subjectRepository.save(subject);
    }

    @Override
    public Subject removeStudent(UUID subjectId, UUID studentId) {
        Subject subject = getById(subjectId);
        subject.getStudents().removeIf(st -> st.getId().equals(studentId));
        return subjectRepository.save(subject);
    }
}
