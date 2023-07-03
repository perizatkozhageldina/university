package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.foxminded.university.model.Student;

import java.util.List;

@Repository
public interface StudentJdbcRepository extends JpaRepository<Student, Long> {
    int countByGroupId(Long groupId);
}