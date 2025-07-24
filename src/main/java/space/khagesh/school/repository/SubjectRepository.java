package space.khagesh.school.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import space.khagesh.school.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

	Optional<Subject> findById(UUID subjectId);

	void deleteById(UUID id);

}
