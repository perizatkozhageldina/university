package ua.foxminded.university.model;

import java.util.List;

public class Group {
    private int id;
    private String name;
    private List<Course> courses;
    private List<Student> students;
    
    public Group (int id, String name, List<Course> courses, List<Student> students) {
        this.setId(id);
        this.setName(name);
        this.setCourses(courses);
        this.setStudents(students);
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
