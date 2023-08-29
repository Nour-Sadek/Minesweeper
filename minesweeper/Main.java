package minesweeper;

import java.util.Scanner;
import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;
import java.lang.Math;


public class Main {
    static final int GRID = 9;
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);
    static final String MINE = "X";
    static final String SAFE = ".";

    public static void main(String[] args) {
        System.out.println("How many mines do you want on the field?");
        String randomField = "";

        // Determine number of mines and safe cells in field
        ArrayList<String> field = new ArrayList<String>();
        int numMines = scanner.nextInt();
        int numSafes = (int) Math.pow(Main.GRID, 2) - numMines;

        // Fill the field list with total number of safe and mine cells
        for (int i = 0; i < numMines; i++) field.add(Main.MINE);
        for (int i = 0; i < numSafes; i++) field.add(Main.SAFE);

        // Randomize the field list
        Collections.shuffle(field, Main.random);

        // Clone the field list
        ArrayList<String> clonedField = (ArrayList) field.clone();

        // Replace the values in the clone array
        ArrayList<Integer> leftSide = new ArrayList<Integer>();
        for (int i = 1; i <= Main.GRID - 2; i++) {
            leftSide.add(i * Main.GRID);
        }

        ArrayList<Integer> rightSide = new ArrayList<Integer>();
        for (int i = 2; i <= Main.GRID - 1; i++) {
            rightSide.add((i * Main.GRID) - 1);
        }


        for (int i = 0; i < Math.pow(Main.GRID, 2); i++) {
            int numOfSurroundingMines = 0;
            ArrayList<Integer> temp = new ArrayList<Integer>();
            if (field.get(i).equals("X")) {
                continue;
            }

            if (i == 0) { // Cell is in the upper-left corner
                temp.add(1);
                temp.add(Main.GRID);
                temp.add(Main.GRID + 1);

            } else if (i >= 1 && i <= Main.GRID - 2) { // Cell is in the upper side
                temp.add(i - 1);
                temp.add(i + 1);
                temp.add(i + Main.GRID);
                temp.add(i + Main.GRID - 1);
                temp.add(i + Main.GRID + 1);

            } else if (i == Main.GRID - 1) { // Cell is in the upper-right corner
                temp.add(i - 1);
                temp.add(i + Main.GRID);
                temp.add(i + Main.GRID - 1);

            } else if (i == Main.GRID * (Main.GRID - 1)) { // Cell is in the lower-left corner
                temp.add(i + 1);
                temp.add(i - Main.GRID);
                temp.add(i - Main.GRID + 1);

            } else if (i >= Main.GRID * (Main.GRID - 1) + 1 && i <= Math.pow(Main.GRID, 2) - 2) { // Cell is in the lower side
                temp.add(i - 1);
                temp.add(i + 1);
                temp.add(i - Main.GRID);
                temp.add(i - Main.GRID - 1);
                temp.add(i - Main.GRID + 1);

            } else if (i == Math.pow(Main.GRID, 2) - 1) { // Cell is in the lower-right corner
                temp.add(i - 1);
                temp.add(i - Main.GRID);
                temp.add(i - Main.GRID - 1);

            } else if (leftSide.contains(i)) { // Cell is in the left side
                temp.add(i - Main.GRID);
                temp.add(i - Main.GRID + 1);
                temp.add(i + 1);
                temp.add(i + Main.GRID);
                temp.add(i + Main.GRID + 1);

            } else if (rightSide.contains(i)) { // Cell is in the right side
                temp.add(i - Main.GRID);
                temp.add(i - Main.GRID - 1);
                temp.add(i - 1);
                temp.add(i + Main.GRID);
                temp.add(i + Main.GRID - 1);

            } else { // Cell is in the middle of the field
                temp.add(i - 1);
                temp.add(i + 1);
                temp.add(i - Main.GRID);
                temp.add(i - Main.GRID - 1);
                temp.add(i - Main.GRID + 1);
                temp.add(i + Main.GRID);
                temp.add(i + Main.GRID - 1);
                temp.add(i + Main.GRID + 1);

            }

            // Update clonedField if at least one mine surrounds current cell
            for (int nearbyCell: temp) if (field.get(nearbyCell).equals("X")) numOfSurroundingMines++;
            if (numOfSurroundingMines != 0) clonedField.set(i, Integer.toString(numOfSurroundingMines));
        }

        // Add them to randomField String to output on console adding \n after 9 cells
        for (int i = 1; i <= clonedField.size(); i++) {
            randomField += clonedField.get(i - 1);
            if (i % Main.GRID == 0) randomField += "\n";
        }

        System.out.print(randomField);
    }
}
