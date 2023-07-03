package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university.model.Teacher;

@Repository
public interface TeacherJdbcRepository extends JpaRepository<Teacher, Long> {
}//