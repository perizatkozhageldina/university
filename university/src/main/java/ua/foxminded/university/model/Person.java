package ua.foxminded.university.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract  class Person {
    protected  int id;
    protected  String name;
    protected  String surname;
    protected  LocalDate birthDate;
    protected  String email;
    protected  Gender gender;   
}
