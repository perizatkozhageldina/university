package ua.foxminded.university.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Lecture {
    private int lectureId;
    private String theme;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Audience audience;
}
