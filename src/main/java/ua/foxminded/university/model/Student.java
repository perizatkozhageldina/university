package ua.foxminded.university.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.university.constraints.MaxStudentsInGroup;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    @Max(value=5)
    @NotNull(message = "Academic year cannot be null.")
    private Integer academicYear;
    @JoinColumn(name = "groupId")
    @MaxStudentsInGroup
    private Long groupId;
}//