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
public class School {
    private int schoolId;
    private String name;
    private List<Department> departments; 
}
