package space.khagesh.school.serviceimpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.khagesh.school.entity.Student;
import space.khagesh.school.entity.Subject;
import space.khagesh.school.repository.StudentRepository;
import space.khagesh.school.repository.SubjectRepository;
import space.khagesh.school.service.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;

    @Override
    public Student create(Student s) { return studentRepo.save(s); }

    @Override
    public Student getById(UUID id) {
        return studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<Student> getAll() { return studentRepo.findAll(); }

    @Override
    public Student update(UUID id, Student s) {
        Student existing = getById(id);
        existing.setName(s.getName());
        return studentRepo.save(existing);
    }

    @Override
    public void delete(UUID id) { studentRepo.deleteById(id); }

    @Override
    public Student addSubject(UUID studentId, UUID subjectId) {
        Student st = getById(studentId);
        Subject sub = subjectRepo.findById(subjectId).orElseThrow(() -> new RuntimeException("Subject not found"));
        st.getSubjects().add(sub);
        return studentRepo.save(st);
    }

    @Override
    public Student removeSubject(UUID studentId, UUID subjectId) {
        Student st = getById(studentId);
        st.getSubjects().removeIf(s -> s.getId().equals(subjectId));
        return studentRepo.save(st);
    }
}

