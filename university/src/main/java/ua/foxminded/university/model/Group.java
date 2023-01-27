package ua.foxminded.university.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Group {
    private int id;
    private String name;
    private List<Course> courses;
    private List<Student> students;
}
