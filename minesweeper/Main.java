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

        // Add them to randomField String to output on console adding \n after 9 cells
        for (int i = 1; i <= field.size(); i++) {
            randomField += field.get(i - 1);
            if (i % Main.GRID == 0) randomField += "\n";
        }

        System.out.print(randomField);
    }
}
