package Entities;

public class Sailor {
    private int affiliation;
    private String name;
    private float weight;
    private int[] skills;
    private int winRate;

    public Sailor(int affiliation, String name, float weight, int[] skills, int winRate) {
        this.affiliation = affiliation;
        this.name = name;
        this.weight = weight;
        this.skills = skills;
        this.winRate = winRate;
    }
    public float getWeight() {
        return weight;
    }
    public int[] getSkills() {
        return skills;
    }
    public int getWinRate() {
        return winRate;
    }
}
