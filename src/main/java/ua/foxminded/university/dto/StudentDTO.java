package ua.foxminded.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.university.constraints.MaxStudentsInGroup;

import javax.persistence.JoinColumn;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentDTO {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    @Max(value=5)
    @NotNull(message = "Academic year cannot be null.")
    private Integer academicYear;
    @MaxStudentsInGroup
    private Long groupId;
}
