package ua.foxminded.university.model;

import java.util.List;

public class Specialization {
    private int id;
    private String name;
    private List<Group> groups;
    
    public Specialization (int id, String name, List<Group> groups) {
        this.setId(id);
        this.setName(name);
        this.setGroups(groups);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
