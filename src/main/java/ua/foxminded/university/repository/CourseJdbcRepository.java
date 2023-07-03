package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university.model.Course;

@Repository
public interface CourseJdbcRepository extends JpaRepository<Course, Long> {
}