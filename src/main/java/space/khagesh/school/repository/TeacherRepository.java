package space.khagesh.school.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import space.khagesh.school.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String> {

    // Option 1: EntityGraph
    @EntityGraph(attributePaths = {"teacherSubjects", "teacherSubjects.subject"})
    Optional<Teacher> findWithTeacherSubjectsById(String id);

    @EntityGraph(attributePaths = {"teacherSubjects", "teacherSubjects.subject"})
    List<Teacher> findAll(); // this will fetch join sets

    // Option 2: JPQL fetch join (alternative to EntityGraph)
    @Query("select t from Teacher t left join fetch t.teacherSubjects ts left join fetch ts.subject where t.id = :id")
    Optional<Teacher> findByIdWithSubjects(String id);
}
