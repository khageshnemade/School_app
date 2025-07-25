package space.khagesh.school.service;

import java.util.List;
import java.util.UUID;

import space.khagesh.school.dto.SubjectDTO;
import space.khagesh.school.entity.Subject;

public interface SubjectService {
    SubjectDTO create(SubjectDTO s);

    SubjectDTO getById(String id);

    List<SubjectDTO> getAll();

    SubjectDTO update(String id, SubjectDTO s);

    void delete(String id);
}
