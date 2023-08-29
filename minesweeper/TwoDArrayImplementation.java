package minesweeper;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import minesweeper.Main;

public class TwoDArrayImplementation {
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("How many mines do you want on the field?");
        int numMines = scanner.nextInt();
        int numMinesInserted = 0;

        String[][] mineField = new String[Main.GRID][Main.GRID];

        // Fill in the array with the default SAFE cell
        for (String[] strings : mineField) Arrays.fill(strings, Main.SAFE);

        // Randomly filling in the mineField with required number of mines
        while (numMinesInserted < numMines) {
            int randX = random.nextInt(Main.GRID);
            int randY = random.nextInt(Main.GRID);

            if (!mineField[randX][randY].equals(Main.MINE)) {
                mineField[randX][randY] = Main.MINE;
                numMinesInserted++;
            }
        }

        // Print the mineField
        printMineField(mineField);
    }

    public static void printMineField(String[][] mineField) {
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                System.out.print(mineField[i][j]);
            }
            System.out.println();
        }
    }
}
