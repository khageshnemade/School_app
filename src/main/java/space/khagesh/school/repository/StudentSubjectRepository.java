package space.khagesh.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import space.khagesh.school.entity.StudentSubject;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long> {}
