package ua.foxminded.university.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(builderMethodName = "teacherBuilder")
public class Teacher extends Person {
    private int salary;
}