package ua.foxminded.university.model;

public class Audience {
    private int id;
    private int room;
    private int capacity;
    
    public Audience(int id, int room, int capacity) {
        this.setId(id);
        this.setRoom(room);
        this.setCapacity(capacity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
