package space.khagesh.school.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import space.khagesh.school.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String>{

	Optional<Student> findById(String id);

	void deleteById(String id);

}
