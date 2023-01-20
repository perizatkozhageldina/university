package ua.foxminded.university.model;

import java.time.LocalDate;

import lombok.Builder;

public class Student extends Person {

    @Builder
    private Student(int id, String name, String surname, LocalDate birthDate, String email, Gender gender) {
        super(id, name, surname, birthDate, email, gender);
    }
}