package ua.foxminded.university.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course {
    private int courseId;
    private String name;
    private String description;
    private int creditHours;
    private List<Lecture> lectures;
    private Teacher teacher;
}
