import Entities.BnBConfig;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HighSpeedSailing {
    public static int[] bestConfig;
    public static float bestSolution = 0;
    private static final EntityManager em = new EntityManager();
    public static long t;
    public void run() {
        Scanner sc = new Scanner(System.in);
        int opt = 0;
        while (opt < 1 || opt > 2) {
            System.out.print("""
                    \nWhat do you want to do?
                        1. Backtracking
                        2. Branch and Bound
                    
                    >\s""");

            try {
                opt = Integer.parseInt(sc.nextLine());
                if (opt < 1 || opt > 2) {
                    System.out.println("\nPlease, enter a valid option");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease, enter a valid option");
            }
        }

        if (opt == 1) {
            int[] config = new int[Main.sailors.size()];
            bestConfig = new int[Main.sailors.size()];
            t = System.currentTimeMillis();

            backtracking(config, 0, Main.boats.size(), Main.sailors.size());
            System.out.println("\n" + ((System.currentTimeMillis() - t) / 1000.0f) + " seconds");
            System.out.println("Best configuration: " + Arrays.toString(bestConfig) + "\nBest solution: " + bestSolution);

        } else  {
            bestConfig = new int[Main.sailors.size()];
            long t = System.currentTimeMillis();

            branchAndBound();
            System.out.println("\n" + ((System.currentTimeMillis() - t) / 1000.0f) + " seconds");
            System.out.println("Best configuration: " + Arrays.toString(bestConfig) + "\nBest solution: " + bestSolution);
        }
    }

    public void branchAndBound() {
        PriorityQueue<BnBConfig> queue = new PriorityQueue<>();
        BnBConfig root = new BnBConfig(Main.boats, Main.sailors);
        queue.add(root);

        while (!queue.isEmpty()) {
            BnBConfig config = queue.poll();

            List<BnBConfig> children = config.expand(Main.boats, Main.sailors);
            for (BnBConfig child : children) {
                if (child.isFull()) {
                    if (child.getTotalSpeed() > bestSolution && child.isBoatsFull()) {
                        bestSolution = child.getTotalSpeed();
                        bestConfig = Arrays.copyOf(child.getConfig(), child.getConfig().length);
                        System.out.println("Config: " + Arrays.toString(child.getConfig()) + " -> Speed:" + child.getTotalSpeed());
                    }
                } else {
                    if (child.getTotalSpeed() > bestSolution) {
                        queue.offer(child);
                    }
                }
            }
        }
    }

    public boolean correct(BnBConfig config) {
        for (int i = 1; i < config.getBoatCapacity().length; i++) {
            if (config.getBoatCapacity()[i] < 0) {
                return false;
            }
        }
        if (Integer.MAX_VALUE - config.getBoatCapacity()[0] > Main.sailors.size() -
                getTotalCapacity(Main.boats.size())) {
            return false;
        }
        return true;
    }
    public void backtracking(int[] config, int k, int numBoats, int numSailors) {
        config[k] = 0;

        while (config[k] < numBoats) {
            Main.boats.get(config[k]).setCurrCapacity(-1);
            Main.boats.get(config[k]).setRealSpeed(em.calculateRealSpeed(config[k], k));

            if (k == numSailors - 1) {

                if (correct(numBoats)) {
                    //System.out.println(Arrays.toString(config));
                    if (getTotalSpeed(numBoats) > bestSolution) {
                        bestConfig = Arrays.copyOf(config, config.length);
                        bestSolution = getTotalSpeed(numBoats);
                        //System.out.println("Best one for now: " + bestSolution);
                    }

                }
            }
            else {
                if (correct(numBoats)) {
                    if (getTotalSpeed(numBoats) > bestSolution) {
                        backtracking(config, k + 1, numBoats, numSailors);
                    }
                }
            }

            Main.boats.get(config[k]).unsetRealSpeed(em.calculateRealSpeed(config[k], k));
            Main.boats.get(config[k]).setCurrCapacity(1);
            config[k]++;
        }
    }

    public boolean correct(int numBoats) {
        for (int i = 1; i < numBoats; i++) {
            if (Main.boats.get(i).getCurrCapacity() < 0) {
                return false;
            }
        }
        if (Main.boats.get(0).getCapacity() - Main.boats.get(0).getCurrCapacity() > Main.sailors.size() -
                getTotalCapacity(numBoats)) {
            return false;
        }
        return true;
    }

    public float getTotalSpeed(int numBoats) {
        float sol = 0;
        for (int i = 1; i < numBoats; i++) {
            sol += Main.boats.get(i).getRealSpeed();
        }
        return sol;
    }

    public int getTotalCapacity(int numBoats) {
        int totalCapacity = 0;
        for (int i = 1; i < numBoats; i++) {
            totalCapacity += Main.boats.get(i).getCapacity();
        }
        return totalCapacity;
    }

}
