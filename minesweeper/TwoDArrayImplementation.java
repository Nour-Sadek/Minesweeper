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
        int numMines = getNumberOfMines();
        int numMinesInserted = 0;

        // Create the mineField
        String[][] mineField = new String[Main.GRID][Main.GRID];

        // Fill in the 2D array with the default UNEXPLORED cell
        for (String[] strings : mineField) Arrays.fill(strings, Main.UNEXPLORED);
        // Create a clone of mineField before modifying it to include hints and mines
        String[][] gameMineField = new String[Main.GRID][Main.GRID];
        for (int row = 0; row < mineField.length; row++) {
            gameMineField[row] = mineField[row].clone();
        }

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
        String[][] filledMineField = includeHints(mineField);
        // Display empty minefield
        printMineFieldWithGrid(gameMineField);
        boolean keepGoing = false;

        while (true) {
            // Collect correct user input
            String[] userInput = gettingUserInput();
            int col = Integer.parseInt(userInput[0]) - 1;
            int row = Integer.parseInt(userInput[1]) - 1;
            String command = userInput[2];

            // Update gameFieldMine accordingly
            switch (command) {
                case Main.MINE_COMMAND -> {
                    if (gameMineField[row][col].equals(Main.UNEXPLORED)) {
                        gameMineField[row][col] = Main.UNEXPLORED_MARKED;
                        printMineFieldWithGrid(gameMineField);
                    } else if (gameMineField[row][col].equals(Main.UNEXPLORED_MARKED)) {
                        gameMineField[row][col] = Main.UNEXPLORED;
                        printMineFieldWithGrid(gameMineField);
                    } else {
                        System.out.println("Can't mark a cell that has been explored!");
                    }
                }
                case Main.FREE_COMMAND -> {
                    // Check if this cell has a mine
                    if (filledMineField[row][col].equals(Main.MINE)) {
                        // Print the game minefield with location of mines shown
                        fillGameFieldWithMine(filledMineField, gameMineField);
                        printMineFieldWithGrid(gameMineField);
                        // Alert the user that they have failed
                        System.out.println("You stepped on a mine and failed!");
                        keepGoing = true;
                    } else {
                        ArrayList<String> possibleCoordinates = possibleCoordinates();
                        ArrayList<String> possibleNumbers = new ArrayList<>();
                        for (int i = 1; i <= 8; i++) possibleNumbers.add(Integer.toString(i));
                        exploreNearbyCells(possibleNumbers, possibleCoordinates, filledMineField, gameMineField, row, col);
                        printMineFieldWithGrid(gameMineField);
                    }
                }
            }

            if (keepGoing) break;

            // Checking if the user won because they exposed all the mines or explored all the safe cells
            if (numMinesExposed(filledMineField, gameMineField) == numMines
                    || numSafesExposed(filledMineField, gameMineField) == (Main.GRID * Main.GRID) - numMines) {
                System.out.println("Congratulations! You found all the mines!");
                break;
            }

        }
    }

    // countUnexplored(gameMineField) == numMines)

    @Deprecated
    public static void printMineField(String[][] mineField) {
        System.out.println();
        for (String[] row : mineField) {
            for (String column : row) System.out.print(column);
            System.out.println();
        }
    }

    public static String[] gettingUserInput() {
        // Determining possible x and y coordinate values
        ArrayList<String> possibleCoordinates = possibleCoordinates();

        // Checking for valid user input
        while (true) {
            System.out.println("Set/unset mines marks or claim a cell as free:");
            String[] userInput = scanner.nextLine().split(" ");

            if (userInput.length != 3) {
                System.out.println("Wrong Input! Please input two numbers and a command, either 'free' or 'mine', with one space between each input.\n");
            } else {
                String x = userInput[0];
                String y = userInput[1];
                String command = userInput[2];

                if (!possibleCoordinates.contains(x) || !possibleCoordinates.contains(y)) {
                    System.out.println("x and y coordinates must be valid numbers that refer to a position on the minefield.\n");
                    continue;
                }

                if (!command.equals(Main.MINE_COMMAND) && !command.equals(Main.FREE_COMMAND)) {
                    System.out.println("Wrong command. Allowed commands are: free, mine\n");
                    continue;
                }

                return new String[] {x, y, command};
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

    @Deprecated
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

    @Deprecated
    public static String[][] replaceMinesWithMarked(String[][] mineField) {
        String[][] output = new String[mineField.length][mineField.length];

        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                if (mineField[i][j].equals(Main.MINE)) {
                    output[i][j] = Main.UNEXPLORED_MARKED;
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
                    output[i][j] = Main.UNEXPLORED;
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

    public static ArrayList<String> possibleCoordinates() {
        ArrayList<String> coordinates = new ArrayList<>();
        for (int i = 1; i <= Main.GRID; i++) coordinates.add(Integer.toString(i));
        return coordinates;
    }

    public static void exploreNearbyCells(ArrayList<String> possibleNumbers, ArrayList<String> possibleCoordinates, String[][] filledMineField, String[][] gameMineField, int row, int col) {
        // Return if impossible coordinates
        if (!possibleCoordinates.contains(String.valueOf(row + 1)) || !possibleCoordinates.contains(String.valueOf(col + 1))) return;

        // Return if explored, looking at gameMineField (has Main.EXPLORED or a number)
        if (gameMineField[row][col].equals(Main.EXPLORED_FREE) || possibleNumbers.contains(gameMineField[row][col])) return;

        // Update gameMineField if cell is Main.UNEXPLORED in filledMineField
        if (filledMineField[row][col].equals(Main.UNEXPLORED)) gameMineField[row][col] = Main.EXPLORED_FREE;

        // Update gameMineField if number is encountered
        if (possibleNumbers.contains(filledMineField[row][col])) {
            gameMineField[row][col] = filledMineField[row][col];
            return;
        }

        // Return if encountered Main.MINE
        if (filledMineField[row][col].equals(Main.MINE)) return;

        // Recursively check all other nearby cells
        exploreNearbyCells(possibleNumbers, possibleCoordinates, filledMineField, gameMineField, row - 1, col);
        exploreNearbyCells(possibleNumbers, possibleCoordinates, filledMineField, gameMineField, row - 1, col - 1);
        exploreNearbyCells(possibleNumbers, possibleCoordinates, filledMineField, gameMineField, row - 1, col + 1);
        exploreNearbyCells(possibleNumbers, possibleCoordinates, filledMineField, gameMineField, row + 1, col);
        exploreNearbyCells(possibleNumbers, possibleCoordinates, filledMineField, gameMineField, row + 1, col - 1);
        exploreNearbyCells(possibleNumbers, possibleCoordinates, filledMineField, gameMineField, row + 1, col + 1);
        exploreNearbyCells(possibleNumbers, possibleCoordinates, filledMineField, gameMineField, row, col - 1);
        exploreNearbyCells(possibleNumbers, possibleCoordinates, filledMineField, gameMineField, row, col + 1);

    }

    public static void fillGameFieldWithMine(String[][] filledMineField, String[][] gameMineField) {
        for (int i = 0; i < filledMineField.length; i++) {
            for (int j = 0; j < filledMineField.length; j++) {
                if (filledMineField[i][j].equals(Main.MINE)) gameMineField[i][j] = Main.MINE;
            }
        }
    }

    public static int numMinesExposed(String[][] filledMineField, String[][] gameMineField) {
        int correctNumMinesExposed = 0;

        for (int i = 0; i < filledMineField.length; i++) {
            for (int j = 0; j < filledMineField.length; j++) {
                if (filledMineField[i][j].equals(Main.MINE)) {
                    if (gameMineField[i][j].equals(Main.UNEXPLORED_MARKED)) correctNumMinesExposed++;
                }
            }
        }

        // Determine the number of mines marked in gameMineField
        int numUnexploredMarked = 0;

        for (int i = 0; i < gameMineField.length; i++) {
            for (int j = 0; j < gameMineField.length; j++) {
                if (gameMineField[i][j].equals(Main.UNEXPLORED_MARKED)) numUnexploredMarked++;
            }
        }

        return (numUnexploredMarked == correctNumMinesExposed) ? correctNumMinesExposed : 0;
    }

    public static int numSafesExposed(String[][] filledMineField, String[][] gameMineField) {
        int correctNumSafesExposed = 0;

        for (int i = 0; i < filledMineField.length; i++) {
            for (int j = 0; j < filledMineField.length; j++) {
                if (!filledMineField[i][j].equals(Main.MINE)) {
                    if (!gameMineField[i][j].equals(Main.UNEXPLORED_MARKED)
                            && !gameMineField[i][j].equals(Main.UNEXPLORED)) correctNumSafesExposed++;
                }
            }
        }

        return correctNumSafesExposed;
    }

    @Deprecated
    public static int countUnexplored(String[][] gameMineField) {
        int numUnexplored = 0;

        for (int i = 0; i < gameMineField.length; i++) {
            for (int j = 0; j < gameMineField.length; j++) {
                if (gameMineField[i][j].equals(Main.UNEXPLORED)) numUnexplored++;
            }
        }

        return numUnexplored;
    }

    public static int getNumberOfMines() {
        while (true) {
            System.out.println("How many mines do you want on the field?");
            String userInput = scanner.nextLine();
            if (!userInput.matches("[0-9]+")) {
                System.out.println("Invalid input! Please provide a single number.\n");
            } else {
                int numMines = Integer.parseInt(userInput);
                if (numMines >= (Main.GRID * Main.GRID)) {
                    System.out.println("Please don't input more mines than there are cells!\n"
                    + "The game will be played on a " + Main.GRID + "x" + Main.GRID
                    + " grid.\nMaximum allowed of mines is " + (Main.GRID * Main.GRID - 1) + ".\n"
                    + "Minimum allowed of mines is 1.\n");
                } else if (numMines == 0) {
                    System.out.println("Please insert at least one mine!\n");
                } else {
                    return numMines;
                }
            }
        }
    }
}
