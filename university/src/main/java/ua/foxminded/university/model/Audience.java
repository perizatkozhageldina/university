package ua.foxminded.university.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Audience {
    private int id;
    private int room;
    private int capacity;
}
