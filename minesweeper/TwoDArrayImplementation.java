package minesweeper;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

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

        // Add hints to the mineField
        String[][] mineFieldWithHints = includeHints(mineField);
        printMineField(mineFieldWithHints);
    }

    public static void printMineField(String[][] mineField) {
        for (String[] row : mineField) {
            for (String column : row) System.out.print(column);
            System.out.println();
        }
    }

    public static String[][] includeHints(String[][] mineField) {
        String[][] output = new String[mineField.length][mineField.length];

        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {

                // Don't check surrounding cells if this cell contains a MINE
                if (mineField[i][j].equals(Main.MINE)) {
                    output[i][j] = Main.MINE;
                    continue;
                }

                // Determine the number of MINEs that surround this cell
                int numOfSurroundingMines = 0;
                for (int row = i - 1; row <= i + 1; row++) {
                    for (int col = j - 1; col <= j + 1; col++) if (hasMine(mineField, row, col)) numOfSurroundingMines++;
                }

                // Adding SAFE "." if cell has no surrounding MINEs, or the number otherwise
                if (numOfSurroundingMines == 0) {
                    output[i][j] = Main.SAFE;
                } else {
                    output[i][j] = Integer.toString(numOfSurroundingMines);
                }
            }
        }
        return output;
    }

    public static boolean hasMine(String[][] mineField, int i, int j) throws ArrayIndexOutOfBoundsException {
        try {
            return mineField[i][j].equals("X");
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}
