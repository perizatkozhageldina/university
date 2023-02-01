package ua.foxminded.university.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    private int personId;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private Gender gender;
}