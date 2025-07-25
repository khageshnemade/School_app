package space.khagesh.school.serviceimpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.khagesh.school.dto.TeacherDTO;
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

    // Helper method to convert Teacher entity to TeacherDTO
    private TeacherDTO toDTO(Teacher teacher) {
        Set<String> subjectIds = teacher.getSubjects().stream()
                .map(subject -> subject.getId().toString())  // Convert UUID to String for subjects
                .collect(Collectors.toSet());

        return TeacherDTO.builder()
                .id(teacher.getId()) 
                .name(teacher.getName())
                .subjectIds(subjectIds)
                .build();
    }

    @Override
    public TeacherDTO getById(String id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return toDTO(teacher);
    }

    @Override
    public List<TeacherDTO> getAll() {
        return teacherRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO update(String id, TeacherDTO t) {
        Teacher existing = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        existing.setName(t.getName());
        // subjects will not be overridden here, you can add/remove subjects separately
        teacherRepository.save(existing);
        return toDTO(existing);
    }

    @Override
    public void delete(String id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public TeacherDTO addSubject(String teacherId, String subjectId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        teacher.getSubjects().add(subject);
        teacher = teacherRepository.save(teacher);
        return toDTO(teacher);
    }

    @Override
    public TeacherDTO removeSubject(String teacherId, String subjectId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.getSubjects().removeIf(s -> s.getId().equals(subjectId));
        teacher = teacherRepository.save(teacher);
        return toDTO(teacher);
    }
}
