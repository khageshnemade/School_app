package space.khagesh.school.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import space.khagesh.school.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

	void deleteById(UUID id);

	Optional<Teacher> findById(UUID id);

}
