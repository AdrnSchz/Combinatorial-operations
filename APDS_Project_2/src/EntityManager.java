import Entities.Boat;
import Entities.Center;
import Entities.Sailor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Integer.MAX_VALUE;

public class EntityManager {
    public List<Sailor> readSailors(String path) {
        try {
            FileReader f = new FileReader(path);
            BufferedReader bf = new BufferedReader(f);
            int n = Integer.parseInt(bf.readLine());
            List<Sailor> sailors = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                String line = bf.readLine();
                StringTokenizer stn = new StringTokenizer(line, ";\n");
                int id = Integer.parseInt(stn.nextToken());
                String name = stn.nextToken();
                float weight = Float.parseFloat(stn.nextToken());
                int[] skills = new int[6];
                for (int j = 0; j < 6; j++) {
                    skills[j] = Integer.parseInt(stn.nextToken());
                }
                int winRate = Integer.parseInt(stn.nextToken());

                Sailor sailor = new Sailor(id, name, weight, skills, winRate);
                sailors.add(sailor);
            }
            return sailors;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Boat> readBoats(String path) {
        try {
            FileReader f = new FileReader(path);
            BufferedReader bf = new BufferedReader(f);
            int n = Integer.parseInt(bf.readLine());
            List<Boat> boats = new ArrayList<>();
            boats.add(new Boat(0, "", "Patí Català", 0, 0, MAX_VALUE, 0, "", 0, ""));

            for (int i = 0; i < n; i++) {
                String line = bf.readLine();
                StringTokenizer stn = new StringTokenizer(line, ";\n");
                int id = Integer.parseInt(stn.nextToken());
                String name = stn.nextToken();
                String type = stn.nextToken();
                float weight = Float.parseFloat(stn.nextToken());
                float length = Float.parseFloat(stn.nextToken());
                int capacity = Integer.parseInt(stn.nextToken());
                int wins = Integer.parseInt(stn.nextToken());
                String state = stn.nextToken();
                int speed = Integer.parseInt(stn.nextToken());
                String center = stn.nextToken();

                Boat boat = new Boat(id, name, type, weight, length, capacity, wins, state, speed, center);
                boats.add(boat);
            }
            return boats;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public float calculateRealSpeed(int boatIndex, int sailorIndex) {
        int skillIndex = switch (Main.boats.get(boatIndex).getType()) {
            case ("Windsurf") -> 0;
            case ("Optimist") -> 1;
            case ("Laser") -> 2;
            case ("Patí Català") -> 3;
            case ("HobieDragoon") -> 4;
            case ("HobieCat") -> 5;
            default -> -1;
        };
        float impactSkill = (float) (((Main.sailors.get(sailorIndex).getSkills()[skillIndex] / 10) +
                (Main.sailors.get(sailorIndex).getWinRate() / 100)) / 2);
        float impactWeight =  (100 - Main.sailors.get(sailorIndex).getWeight()) / Main.boats.get(boatIndex).getWeight();

        return (impactSkill + impactWeight) / 2;
    }

    public List<Center> setCenters() {
        List<Center> centers = new ArrayList<>();
        int index, i = 0;
        for (i = 1; i < Main.boats.size(); i++) {
            index = centerIsUnique(Main.boats.get(i).getCenter(), centers);
            if (index == - 1) {
                centers.add(new Center(Main.boats.get(i).getCenter()));
                index = centers.size() - 1;
            }
            if (typeIsUnique(Main.boats.get(i).getType(), centers)) {
                centers.get(index).setNum_types(1);
                centers.get(index).getTypes().add(Main.boats.get(i).getType());
            }
        }
        return centers;
    }

    public int centerIsUnique(String center, List<Center> centers) {
        for (int i = 0; i < centers.size(); i++) {
            if (centers.get(i).getName().equals(center)) {
                return i;
            }
        }
        return - 1;
    }

    public boolean typeIsUnique(String type, List<Center> centers) {
        for (int i = 0; i < centers.size(); i++) {
            for (int j = 0; j < centers.get(i).getNum_types(); j++) {
                if (centers.get(i).getTypes().get(j).equals(type)) {
                    return false;
                }
            }
        }
        return true;
    }
}
