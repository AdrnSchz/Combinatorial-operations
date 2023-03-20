import Entities.Center;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FullFleet {
    private static final int NUM_TYPES = 6;
    public static List<Center> centers;
    public static int[] config;
    public static int[] bestConfig;
    public static int solution;
    public static int bestSolution = Integer.MAX_VALUE;
    private static final EntityManager em = new EntityManager();
    void run() {
        Scanner sc = new Scanner(System.in);
        int opt = 0;
        centers = em.setCenters();

        while (opt < 1 || opt > 2) {
            System.out.print("""
                    \nWhat do you want to do?
                        1. Backtracking
                        2. Greedy
                    
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

        config = new int[centers.size()];
        bestConfig = new int[centers.size()];

        if (opt == 1) {
            long t = System.nanoTime();
            backtracking(0);
            System.out.println("\n" + ((System.nanoTime() - t) / 1000000.0f) + " microseconds");
            if (bestSolution != Integer.MAX_VALUE) {
                System.out.println("Best configuration: " + Arrays.toString(bestConfig) + "\nBest solution: " + bestSolution);
            } else {
                System.out.println("No solution found");
            }
        } else  {
            long t = System.nanoTime();
            centers.sort((c1, c2) -> c2.getNum_types() - c1.getNum_types());
            greedy(0);

            System.out.println("\n" + ((System.nanoTime() - t) / 1000000.0f) + " microseconds");
            if (correct()) {
                System.out.println("Configuration: " + Arrays.toString(config) + "\nSolution: " + getSolution());
            } else {
                System.out.println("No solution found");
            }

        }
    }

    public void greedy(int k) {
        for (int i = 0; i < config.length; i++) {
            config[i]++;
            if (correct()) {
                break;
            }
        }
    }
    public void backtracking(int k) {
        config[k] = 0;

        while (config[k] <= 1) {
            getSolution();
            if (k == centers.size() - 1) {
                if (correct()) {
                    if (solution < bestSolution) {
                        bestSolution = solution;
                        bestConfig = config.clone();
                    }
                }
            } else {
                if (solution < bestSolution) {
                    backtracking(k + 1);
                }
            }
            config[k]++;
        }
    }
    public int getSolution() {
        solution = 0;
        for (int i : config) {
            if (i == 1) {
                solution++;
            }
        }
        return solution;
    }

    public boolean correct() {
        int sol = 0;
        int[] types = getTypes();
        for (int type : types) {
            if (type > 0) {
                sol++;
            }
        }
        return sol == NUM_TYPES;
    }

    public int[] getTypes() {
        int[] types = new int[NUM_TYPES];

        for (int i = 0; i < config.length; i++) {
            if (config[i] == 1) {
                for (int j = 0; j < centers.get(i).getNum_types(); j++) {
                    int type = switch (centers.get(i).getTypes().get(j)) {
                        case ("Windsurf") -> 0;
                        case ("Optimist") -> 1;
                        case ("Laser") -> 2;
                        case ("Patí Català") -> 3;
                        case ("HobieDragoon") -> 4;
                        case ("HobieCat") -> 5;
                        default -> -1;
                    };
                    types[type] += 1;
                }
            }
        }
        return types;
    }

}
