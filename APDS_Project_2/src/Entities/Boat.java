package Entities;

public class Boat {
    private int id;
    private String name;
    private String type;
    private float weight;
    private float length;
    private int capacity;
    private int wins;
    private String state;
    private int speed;
    private String center;
    private float realSpeed;
    private int currCapacity;

    public Boat(int id, String name, String type, float weight, float length, int capacity, int wins, String state, int speed, String center) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.length = length;
        this.capacity = capacity;
        this.wins = wins;
        this.state = state;
        this.speed = speed;
        this.center = center;
        this.realSpeed = speed;
        this.currCapacity = capacity;
    }
    public String getType() {
        return type;
    }
    public float getWeight() {
        return weight;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getSpeed() {
        return speed;
    }
    public String getCenter() {
        return center;
    }
    public float getRealSpeed() {
        return realSpeed;
    }
    public void setRealSpeed(float realSpeed) {
        this.realSpeed *= realSpeed;
    }
    public void unsetRealSpeed(float realSpeed) {
        this.realSpeed /= realSpeed;
    }
    public int getCurrCapacity() {
        return currCapacity;
    }
    public void setCurrCapacity(int currCapacity) {
        this.currCapacity += currCapacity;
    }
}
