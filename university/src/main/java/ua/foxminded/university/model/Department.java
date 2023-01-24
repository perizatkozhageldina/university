package ua.foxminded.university.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Department {
    private int id;
    private String name;
    private List<Specialization> specializations;
    private List<Teacher> teachers;
}
