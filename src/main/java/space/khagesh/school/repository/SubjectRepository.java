package space.khagesh.school.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import space.khagesh.school.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, String> {

	Optional<Subject> findById(String subjectId);

	void deleteById(String id);

}
