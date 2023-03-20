import Entities.Boat;
import Entities.Sailor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static List<Sailor> sailors;
    public static List<Boat> boats;
    public static void main(String[] args) {
        EntityManager em = new EntityManager();
        HighSpeedSailing hss = new HighSpeedSailing();
        FullFleet ff = new FullFleet();
        Scanner sc = new Scanner(System.in);
        String size = "";

        while (!size.equals("XS") && !size.equals("S") && !size.equals("M") && !size.equals("L")) {
            System.out.print("""
                    Which datasets do you wan to use:
                        - XS
                        - S
                        - M
                        - L
                    
                    Please, enter a size:\s""");

            size = sc.nextLine().toUpperCase();

            if (!size.equals("XS") && !size.equals("S") && !size.equals("M") && !size.equals("L")) {
                System.out.println("\nPlease, enter a valid size\n");
            }
        }

        sailors = new ArrayList<>(Objects.requireNonNull(em.readSailors("Datasets/Sailors/sailors" + size + ".txt")));
        boats = new ArrayList<>(Objects.requireNonNull(em.readBoats("Datasets/Boats/boats" + size + ".txt")));

        int opt = 0;
        while (opt < 1 || opt > 2) {
            System.out.print("""
                    \nWhat do you want to do?
                        1. High speed sailing
                        2. Full fleet
                    
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
            hss.run();
        } else {
            ff.run();
        }
    }
}
