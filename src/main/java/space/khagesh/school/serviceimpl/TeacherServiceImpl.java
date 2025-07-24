package space.khagesh.school.serviceimpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.khagesh.school.entity.Subject;
import space.khagesh.school.entity.Teacher;
import space.khagesh.school.repository.SubjectRepository;
import space.khagesh.school.repository.TeacherRepository;
import space.khagesh.school.service.TeacherService;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public Teacher create(Teacher t) {
        return teacherRepository.save(t);
    }

    @Override
    public Teacher getById(UUID id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    @Override
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher update(UUID id, Teacher t) {
        Teacher existing = getById(id);
        existing.setName(t.getName());
        // subjects को यहाँ override नहीं कर रहे (चाहें तो कर सकते हैं)
        return teacherRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public Teacher addSubject(UUID teacherId, UUID subjectId) {
        Teacher teacher = getById(teacherId);
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        teacher.getSubjects().add(subject);
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher removeSubject(UUID teacherId, UUID subjectId) {
        Teacher teacher = getById(teacherId);
        teacher.getSubjects().removeIf(s -> s.getId().equals(subjectId));
        return teacherRepository.save(teacher);
    }
}
