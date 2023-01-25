package ua.foxminded.university.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Person {
    private int id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private Gender gender;
}