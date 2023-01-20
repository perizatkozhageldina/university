package ua.foxminded.university.model;

import java.util.List;

import lombok.Builder;

@Builder
public class School {
    private int id;
    private String name;
    private List<Department> departments; 
}
