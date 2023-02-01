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
public class Department {
    private int departmentId;
    private String name;
    private List<Specialization> specializations;
    private List<Teacher> teachers;
}
