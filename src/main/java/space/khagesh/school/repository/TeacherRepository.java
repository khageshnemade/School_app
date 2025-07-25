package space.khagesh.school.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import space.khagesh.school.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String> {

	void deleteById(String id);

	Optional<Teacher> findById(String id);
	  @Query("SELECT t FROM Teacher t LEFT JOIN FETCH t.subjects WHERE t.id = :id")
	    Optional<Teacher> findByIdWithSubjects(String id); 

}
