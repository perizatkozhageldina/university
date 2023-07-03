package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university.model.Room;

@Repository
public interface RoomJdbcRepository extends JpaRepository<Room, Long> {
}//