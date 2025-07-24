package space.khagesh.school.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import space.khagesh.school.entity.Student;

public interface StudentRepository extends JpaRepository<Student, UUID>{

	Optional<Student> findById(UUID id);

	void deleteById(UUID id);

}
