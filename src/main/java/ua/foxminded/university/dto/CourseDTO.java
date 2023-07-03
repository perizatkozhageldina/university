package ua.foxminded.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CourseDTO {
    private long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Max(value=10)
    @NotNull(message = "Level cannot be null.")
    private int level;
    @NotNull(message = "Hours cannot be null.")
    private int hours;
}//