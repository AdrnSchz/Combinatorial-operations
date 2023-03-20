package Entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BnBConfig implements Comparable<BnBConfig> {
    private int[] config;
    private int[] boatCapacity;
    private float[] speed;
    private int level;
    private List<Boat> boats;
    private List<Sailor> sailors;


    public BnBConfig(List<Boat> boats, List<Sailor> sailors) {
        config = new int[sailors.size()];
        boatCapacity = new int[boats.size()];
        speed = new float[boats.size()];
        for (int i = 0; i < boats.size(); i++) {
            boatCapacity[i] = boats.get(i).getCapacity();
            speed[i] = boats.get(i).getSpeed();
        }

        config[0] = 0;
        boatCapacity[0]--;
        speed[0] *= calculateRealSpeed(1, 0, boats, sailors);
        level = 0;

        this.boats = boats;
        this.sailors = sailors;
    }

    public BnBConfig(BnBConfig that) {
        this.config = that.config.clone();
        this.boatCapacity = that.boatCapacity.clone();
        this.level = that.level;
        this.speed = that.speed.clone();
        this.boats = that.boats;
        this.sailors = that.sailors;
    }

    public boolean isFull() {
        return level == config.length;
    }
    public boolean isBoatsFull() {
        return this.getTotalCapacity() == 0;
    }
    public List<BnBConfig> expand(List<Boat> boats, List<Sailor> sailors) {
        List<BnBConfig> children = new ArrayList<>();

        for (int i = 0; i < boats.size(); i++) {
            if (level <= config.length) {
                if (boatCapacity[i] > 0) {
                    BnBConfig newConfig = new BnBConfig(this);

                    newConfig.config[level] = i;
                    newConfig.boatCapacity[i]--;
                    newConfig.speed[i] *= calculateRealSpeed(i, level, boats, sailors);

                    newConfig.level++;

                    children.add(newConfig);
                }
            }
        }

        return children;
    }
    public float getTotalSpeed() {
        float totalSpeed = 0;
        for (int i = 1; i < speed.length; i++) {
            totalSpeed += speed[i];
        }
        return totalSpeed;
    }

    public int getTotalCapacity() {
        int totalCapacity = 0;
        for (int i = 1; i < boatCapacity.length; i++) {
            totalCapacity += boatCapacity[i];
        }
        return totalCapacity;
    }
    @Override
    public int compareTo(BnBConfig that) {
        return Float.compare(this.getQuality(), that.getQuality());
    }
    public float getQuality() {
        float quality = 0;
        for (int i = 1; i < level; i++) {
            quality += calculateRealSpeed(config[i], i, boats, sailors);
        }
        return quality / level;
    }
    public int[] getConfig() {
        return config;
    }
    public float calculateRealSpeed(int boatIndex, int sailorIndex, List<Boat> boats, List<Sailor> sailors) {
        int skillIndex = switch (boats.get(boatIndex).getType()) {
            case ("Windsurf") -> 0;
            case ("Optimist") -> 1;
            case ("Laser") -> 2;
            case ("Patí Català") -> 3;
            case ("HobieDragoon") -> 4;
            case ("HobieCat") -> 5;
            default -> -1;
        };
        float impactSkill = (float) (((sailors.get(sailorIndex).getSkills()[skillIndex] / 10) +
                (sailors.get(sailorIndex).getWinRate() / 100)) / 2);
        float impactWeight =  (100 - sailors.get(sailorIndex).getWeight()) / boats.get(boatIndex).getWeight();

        return (impactSkill + impactWeight) / 2;
    }

    public int[] getBoatCapacity() {
        return boatCapacity;
    }

    public float[] getSpeed() {
        return speed;
    }

    public int getLevel() {
        return level;
    }
}
