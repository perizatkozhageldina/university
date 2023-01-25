package ua.foxminded.university.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Specialization {
    private int id;
    private String name;
    private List<Group> groups;
}
