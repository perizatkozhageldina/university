package ua.foxminded.university.model;

import java.util.List;

public class Course {
    private int id;
    private String name;
    private String description;
    private int creditHours;
    private List<Lecture> lectures;
    private Teacher teacher;
    
    public Course(int id, String name, String description, int creditHours, List<Lecture> lectures, Teacher teacher) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setCreditHours(creditHours);
        this.setLectures(lectures);
        this.setTeacher(teacher);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
