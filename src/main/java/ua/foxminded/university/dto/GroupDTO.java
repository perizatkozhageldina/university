package ua.foxminded.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupDTO {
    private long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Fill in maximum size of students in group.")
    @Min(0)
    private int maxStudents;
}//