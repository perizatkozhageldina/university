package ua.foxminded.university.model;

import java.time.LocalDate;

import lombok.Builder;

public class Teacher extends Person {
    private int salary;

    @Builder
    private Teacher(int id, String name, String surname, LocalDate birthDate, String email, Gender gender, int salary) {
        super(id, name, surname, birthDate, email, gender);
        this.salary = salary;
    }
}