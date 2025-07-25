package space.khagesh.school.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.khagesh.school.dto.SubjectDTO;
import space.khagesh.school.entity.Subject;
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
    public SubjectDTO create(SubjectDTO s) {
        Subject subject = new Subject();
        subject.setName(s.getName());
        // Save to DB
        Subject savedSubject = subjectRepository.save(subject);
        return toDTO(savedSubject);
    }

    @Override
    public SubjectDTO getById(String id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        return toDTO(subject);
    }

    @Override
    public List<SubjectDTO> getAll() {
        return subjectRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubjectDTO update(String id, SubjectDTO s) {
        Subject existing = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        existing.setName(s.getName());
        Subject updatedSubject = subjectRepository.save(existing);
        return toDTO(updatedSubject);
    }

    @Override
    public void delete(String id) {
        subjectRepository.deleteById(id);
    }
}
