package space.khagesh.school.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import space.khagesh.school.api.ApiResponse;
import space.khagesh.school.dto.StudentDTO;
import space.khagesh.school.entity.Student;
import space.khagesh.school.entity.StudentSubject;
import space.khagesh.school.entity.Subject;
import space.khagesh.school.exception.StudentNotFoundException;
import space.khagesh.school.exception.SubjectNotFoundException;
import space.khagesh.school.repository.StudentRepository;
import space.khagesh.school.repository.StudentSubjectRepository;
import space.khagesh.school.repository.SubjectRepository;
import space.khagesh.school.service.StudentService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;
    private final StudentSubjectRepository studentSubjectRepo;

    private StudentDTO convertToDTO(Student student) {
        Set<String> subjectIds = student.getStudentSubjects().stream()
                .map(ss -> ss.getSubject().getId())
                .collect(Collectors.toSet());

        return StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .subjectIds(subjectIds)
                .build();
    }

    @Override
    public ApiResponse<StudentDTO> getById(String id) {
        Student student = studentRepo.findById(id)
        		.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        return ApiResponse.<StudentDTO>builder()
                .success(true)
                .message("Student fetched successfully")
                .data(convertToDTO(student))
                .build();
    }

    @Override
    public ApiResponse<List<StudentDTO>> getAll() {
        List<StudentDTO> list = studentRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.<List<StudentDTO>>builder()
                .success(true)
                .message("Students fetched successfully")
                .data(list)
                .build();
    }

    @Override
    public ApiResponse<StudentDTO> update(String id, StudentDTO studentDTO) {
        Student student = studentRepo.findById(id)
        		.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        student.setName(studentDTO.getName());
        student = studentRepo.save(student);

        return ApiResponse.<StudentDTO>builder()
                .success(true)
                .message("Student updated successfully")
                .data(convertToDTO(student))
                .build();
    }

    @Override
    public ApiResponse<String> delete(String id) {
        Student student = studentRepo.findById(id)
        		.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        student.getStudentSubjects().forEach(studentSubjectRepo::delete);
        studentRepo.delete(student);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Student deleted successfully")
                .data("Deleted ID: " + id)
                .build();
    }

    @Override
    public ApiResponse<StudentDTO> addSubject(String studentId, String subjectId) {
        Student student = studentRepo.findById(studentId)
        		.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        Subject subject = subjectRepo.findById(subjectId)
        		.orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + subjectId));

        boolean alreadyPresent = student.getStudentSubjects().stream()
                .anyMatch(ss -> ss.getSubject().getId().equals(subjectId));

        if (!alreadyPresent) {
            StudentSubject ss = StudentSubject.builder()
                    .student(student)
                    .subject(subject)
                    .build();
            student.getStudentSubjects().add(ss);
            studentSubjectRepo.save(ss);
        }

        return ApiResponse.<StudentDTO>builder()
                .success(true)
                .message("Subject added to student successfully")
                .data(convertToDTO(student))
                .build();
    }

    @Override
    public ApiResponse<StudentDTO> removeSubject(String studentId, String subjectId) {
        Student student = studentRepo.findById(studentId)
        		.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        student.getStudentSubjects().removeIf(ss -> ss.getSubject().getId().equals(subjectId));
        student = studentRepo.save(student);

        return ApiResponse.<StudentDTO>builder()
                .success(true)
                .message("Subject removed from student successfully")
                .data(convertToDTO(student))
                .build();
    }
}
