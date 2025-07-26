package space.khagesh.school.serviceimpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import space.khagesh.school.dto.TeacherDTO;
import space.khagesh.school.entity.Subject;
import space.khagesh.school.entity.Teacher;
import space.khagesh.school.entity.TeacherSubject;
import space.khagesh.school.exception.TeacherNotFoundException;
import space.khagesh.school.repository.SubjectRepository;
import space.khagesh.school.repository.TeacherRepository;
import space.khagesh.school.repository.TeacherSubjectRepository;
import space.khagesh.school.service.TeacherService;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;

    // Convert Teacher -> TeacherDTO
    private TeacherDTO toDTO(Teacher teacher) {
        Set<String> subjectIds = teacher.getTeacherSubjects()
                .stream()
                .map(ts -> ts.getSubject().getId())
                .collect(Collectors.toSet());

        return TeacherDTO.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .subjectIds(subjectIds)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherDTO getById(String id) {
        Teacher teacher = teacherRepository.findWithTeacherSubjectsById(id)
        		.orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + id));
        return toDTO(teacher);
    }



    @Override
    @Transactional(readOnly = true)
    public List<TeacherDTO> getAll() {
        // Because of @EntityGraph on findAll, subjects will be loaded
        return teacherRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO update(String id, TeacherDTO t) {
        Teacher existing = teacherRepository.findWithTeacherSubjectsById(id)
        		.orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + id));
        existing.setName(t.getName());
        // We are NOT overriding subjects here. They are handled by add/removeSubject.

        return toDTO(existing);
    }

    @Override
    public void delete(String id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public TeacherDTO addSubject(String teacherId, String subjectId) {
        Teacher teacher = teacherRepository.findWithTeacherSubjectsById(teacherId)
        		.orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        // Avoid duplicates
        boolean alreadyPresent = teacher.getTeacherSubjects().stream()
                .anyMatch(ts -> ts.getSubject().getId().equals(subjectId));
        if (!alreadyPresent) {
            TeacherSubject ts = TeacherSubject.builder()
                    .teacher(teacher)
                    .subject(subject)
                    .build();
            teacher.getTeacherSubjects().add(ts);
            teacherSubjectRepository.save(ts);
        }
        return toDTO(teacher);
    }


    @Override
    public TeacherDTO removeSubject(String teacherId, String subjectId) {
        Teacher teacher = teacherRepository.findWithTeacherSubjectsById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));

        teacher.getTeacherSubjects().removeIf(ts -> ts.getSubject().getId().equals(subjectId));
        return toDTO(teacher);
    }

}
