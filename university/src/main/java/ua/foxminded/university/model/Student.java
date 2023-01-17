package ua.foxminded.university.model;

import java.util.Date;

public class Student extends Person {

    public Student(int id, String name, String surname, Date birthDate, String email, String gender) {
        super(id, name, surname, birthDate, email, gender);
    }

}
