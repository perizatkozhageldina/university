package ua.foxminded.university.model;

import java.util.Date;

public class Lecture {
    private int id;
    private String theme;
    private Date startTime;
    private Date endTime;
    private Audience audience;
    
    public Lecture(int id, String theme, Date startTime, Date endTime, Audience audience) {
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }
}
