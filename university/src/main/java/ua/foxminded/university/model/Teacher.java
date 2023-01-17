package ua.foxminded.university.model;

import java.util.Date;

public class Teacher extends Person {
    private int salary;

    public Teacher(int id, String name, String surname, Date birthDate, String email, String gender, int salary) {
        super(id, name, surname, birthDate, email, gender);
        this.setSalary(salary);
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}