package space.khagesh.school.serviceimpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.khagesh.school.dto.StudentDTO;
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

    // Convert Entity to DTO with subject IDs only
    private StudentDTO convertToDTO(Student student) {
        Set<String> subjectIds = student.getSubjects().stream()
            .map(Subject::getId)
            .collect(Collectors.toSet()); 

        return new StudentDTO(student.getId(), student.getName(), subjectIds);
    }

    // Convert DTO to Entity
    private Student convertToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        // You can add logic to set subjects if necessary, but it’s not required here
        return student;
    }

    @Override
    public StudentDTO getById(String id) {
        Student student = studentRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        return convertToDTO(student);
    }

    @Override
    public List<StudentDTO> getAll() {
        List<Student> students = studentRepo.findAll();
        return students.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public StudentDTO update(String id, StudentDTO studentDTO) {
        Student existingStudent = studentRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        existingStudent.setName(studentDTO.getName());
        existingStudent = studentRepo.save(existingStudent);
        return convertToDTO(existingStudent);
    }

    @Override
    public void delete(String id) {
        studentRepo.deleteById(id);
    }

    @Override
    public StudentDTO addSubject(String studentId, String subjectId) {
        Student student = studentRepo.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepo.findById(subjectId)
            .orElseThrow(() -> new RuntimeException("Subject not found"));

        student.getSubjects().add(subject);
        student = studentRepo.save(student);
        return convertToDTO(student);
    }

    @Override
    public StudentDTO removeSubject(String studentId, String subjectId) {
        Student student = studentRepo.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        student.getSubjects().removeIf(s -> s.getId().equals(subjectId));
        student = studentRepo.save(student);
        return convertToDTO(student);
    }
}
