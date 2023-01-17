package ua.foxminded.university.model;

import java.util.List;

public class Department {
    private int id;
    private String name;
    private List<Specialization> specializations;
    private List<Teacher> teachers;
    
    public Department (int id, String name, List<Specialization> specializations, List<Teacher> teachers) {
        this.setId(id);
        this.setName(name);
        this.setSpecializations(specializations);
        this.setTeachers(teachers);
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

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Specialization> specializations) {
        this.specializations = specializations;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
