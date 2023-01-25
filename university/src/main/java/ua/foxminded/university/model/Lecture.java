package ua.foxminded.university.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Lecture {
    private int id;
    private String theme;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Audience audience;
}
