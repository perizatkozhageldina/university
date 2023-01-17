package ua.foxminded.university.model;

import java.util.Date;

abstract class Person {
    private int id;
    private String name;
    private String surname;
    private Date birthDate;
    private String email;
    private String gender;
    
    protected Person(int id, String name, String surname, Date birthDate, String email, String gender) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setGender(gender);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
