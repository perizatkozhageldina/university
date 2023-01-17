package ua.foxminded.university.model;

import java.util.List;

public class School {
    private int id;
    private String name;
    private List<Department> departments;
    
    public School (int id, String name, List<Department> departments) {
        this.setId(id);
        this.setName(name);
        this.setDepartments(departments);
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }  
}
