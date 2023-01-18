package ua.foxminded.university.model;

import java.time.LocalDateTime;

public class Lecture {
    private int id;
    private String theme;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Audience audience;
    
    public Lecture(int id, String theme, LocalDateTime startTime, LocalDateTime endTime, Audience audience) {
        this.setId(id);
        this.setTheme(theme);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setAudience(audience);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }
}
