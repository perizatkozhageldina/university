package ua.foxminded.university.model;

import java.util.List;

import lombok.Builder;

@Builder
public class Group {
    private int id;
    private String name;
    private List<Course> courses;
    private List<Student> students;
}
