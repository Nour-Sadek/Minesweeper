package minesweeper;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class TwoDArrayImplementation {
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Get the number of mines
        System.out.println("How many mines do you want on the field?");
        int numMines = scanner.nextInt();
        int numMinesInserted = 0;

        // Create the mineField
        String[][] mineField = new String[Main.GRID][Main.GRID];

        // Fill in the 2D array with the default SAFE cell
        for (String[] strings : mineField) Arrays.fill(strings, Main.SAFE);

        // Randomly replacing SAFE cells in the mineField with required number of MINEs
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
        // Hide the location of MINEs
        String[][] hiddenMineField = hideMines(mineFieldWithHints);
        // Print the mineField with MINEs hidden
        printMineFieldWithGrid(hiddenMineField);
        // Replace MINEs with MARKED in <mineFieldWithHints>
        String[][] exposedMineField = replaceMinesWithMarked(mineFieldWithHints);

        // Let the user play the game!
        while (!Arrays.deepEquals(exposedMineField, hiddenMineField)) {
            // Get correct user input
            int[] userInput = gettingUserInput();
            int col = userInput[0] - 1;
            int row = userInput[1] - 1;

            // Mark user input on the minefield
            if (hiddenMineField[row][col].equals(Main.SAFE)) {
                hiddenMineField[row][col] = Main.MARKED;
            } else if (hiddenMineField[row][col].equals(Main.MARKED)) {
                hiddenMineField[row][col] = Main.SAFE;
            } else {
                System.out.println("There is a number here!");
                continue;
            }

            // Print the modified minefield
            printMineFieldWithGrid(hiddenMineField);
        }

        // If while loop breaks, that means user has guessed the location of the MINEs correctly
        System.out.println("Congratulations! You found all the mines!");
    }

    @Deprecated
    public static void printMineField(String[][] mineField) {
        System.out.println();
        for (String[] row : mineField) {
            for (String column : row) System.out.print(column);
            System.out.println();
        }
    }

    public static int[] gettingUserInput() {
        // Determining possible x and u coordinate values
        ArrayList<String> possibleCoordinates = new ArrayList<>();
        for (int i = 1; i <= Main.GRID; i++) possibleCoordinates.add(Integer.toString(i));

        // Checking for valid user input
        while (true) {
            System.out.println("Set/delete mines marks (x and y coordinates):");
            String userInput = scanner.nextLine();

            if (userInput.length() != 3 || !Character.toString(userInput.charAt(1)).equals(" ")) {
                System.out.println("Wrong Input! Please input two one-digit numbers separated by one space.");
            } else {
                String x = String.valueOf(userInput.charAt(0));
                String y = String.valueOf(userInput.charAt(2));

                if (!possibleCoordinates.contains(x) || !possibleCoordinates.contains(y)) {
                    System.out.println("x and y coordinates must be valid numbers that refer to a position on the mine field.");
                    continue;
                }

                return new int[] {Integer.parseInt(x), Integer.parseInt(y)};
            }
        }
    }

    public static void printMineFieldWithGrid(String[][] mineField) {
        System.out.println();
        // Printing the first line
        System.out.print(" |");
        for (int i = 1; i <= Main.GRID; i++) System.out.print(i);
        System.out.println("|");

        // Printing the second line
        System.out.print("-|");
        for (int i = 1; i <= Main.GRID; i++) System.out.print("-");
        System.out.println("|");

        // Printing the mineField
        for (int i = 0; i < mineField.length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < mineField[i].length; j++) System.out.print(mineField[i][j]);
            System.out.println("|");
        }

        // Printing the last line
        System.out.print("-|");
        for (int i = 1; i <= Main.GRID; i++) System.out.print("-");
        System.out.println("|");
    }

    public static String[][] hideMines(String[][] mineField) {
        String[][] output = new String[mineField.length][mineField.length];

        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                String cell = mineField[i][j].equals("X") ? "." : mineField[i][j];
                output[i][j] = cell;
            }
        }

        return output;
    }

    public static String[][] replaceMinesWithMarked(String[][] mineField) {
        String[][] output = new String[mineField.length][mineField.length];

        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                if (mineField[i][j].equals(Main.MINE)) {
                    output[i][j] = Main.MARKED;
                } else {
                    output[i][j] = mineField[i][j];
                }
            }
        }

        return output;
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
