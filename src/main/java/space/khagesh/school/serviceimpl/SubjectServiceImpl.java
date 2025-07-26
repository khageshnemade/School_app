package space.khagesh.school.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.khagesh.school.api.ApiResponse;
import space.khagesh.school.dto.SubjectDTO;
import space.khagesh.school.entity.Subject;
import space.khagesh.school.exception.SubjectNotFoundException;
import space.khagesh.school.repository.SubjectRepository;
import space.khagesh.school.service.SubjectService;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    // Helper method to convert Subject to SubjectDTO
    private SubjectDTO toDTO(Subject subject) {
        return SubjectDTO.builder()
                .id(subject.getId())
                .name(subject.getName())
                .build();
    }

    @Override
    public ApiResponse<SubjectDTO> create(SubjectDTO s) {
        Subject subject = new Subject();
        subject.setName(s.getName());
        Subject savedSubject = subjectRepository.save(subject);

        return ApiResponse.<SubjectDTO>builder()
                .success(true)
                .message("Subject created successfully")
                .data(toDTO(savedSubject))
                .build();
    }

    @Override
    public ApiResponse<SubjectDTO> getById(String id) {
        Subject subject = subjectRepository.findById(id)
        		.orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + id));
        return ApiResponse.<SubjectDTO>builder()
                .success(true)
                .message("Subject fetched successfully")
                .data(toDTO(subject))
                .build();
    }

    @Override
    public ApiResponse<List<SubjectDTO>> getAll() {
        List<SubjectDTO> list = subjectRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return ApiResponse.<List<SubjectDTO>>builder()
                .success(true)
                .message("Subjects fetched successfully")
                .data(list)
                .build();
    }

    @Override
    public ApiResponse<SubjectDTO> update(String id, SubjectDTO s) {
        Subject existing = subjectRepository.findById(id)
        		.orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + id));
        existing.setName(s.getName());
        Subject updatedSubject = subjectRepository.save(existing);

        return ApiResponse.<SubjectDTO>builder()
                .success(true)
                .message("Subject updated successfully")
                .data(toDTO(updatedSubject))
                .build();
    }

    @Override
    public ApiResponse<String> delete(String id) {
        if (!subjectRepository.existsById(id)) {
    		throw new SubjectNotFoundException("Subject not found with id: " + id);
        }
        subjectRepository.deleteById(id);
        return ApiResponse.<String>builder()
                .success(true)
                .message("Subject deleted successfully")
                .data("Deleted ID: " + id)
                .build();
    }
}
