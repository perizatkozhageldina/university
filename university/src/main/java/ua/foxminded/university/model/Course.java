package ua.foxminded.university.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Course {
    private int id;
    private String name;
    private String description;
    private int creditHours;
    private List<Lecture> lectures;
    private Teacher teacher;
}
