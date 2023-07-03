package ua.foxminded.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDTO {
    private long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Capacity cannot be null.")
    private int capacity;
}
//