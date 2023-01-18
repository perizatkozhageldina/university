package ua.foxminded.university.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(builderMethodName = "studentBuilder")
public class Student extends Person {
    
}
