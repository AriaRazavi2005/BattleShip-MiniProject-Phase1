import java.util.Random;
import java.util.Scanner;

public class BattleShip {

    static final int GRID_SIZE = 10;
    static final char WATER_CHAR = '~';
    static final char SHIP_CHAR = 'S';
    static final char HIT_CHAR = 'X';
    static final char MISS_CHAR = 'O';

    static char[][] player1Grid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2Grid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player1TrackingGrid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2TrackingGrid = new char[GRID_SIZE][GRID_SIZE];
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static int[] shipSizes = {4, 3, 3, 2};

    public static void main(String[] args) {
        initializeGrid(player1Grid);
        initializeGrid(player2Grid);
        initializeGrid(player1TrackingGrid);
        initializeGrid(player2TrackingGrid);

        placeShips(player1Grid);
        placeShips(player2Grid);

        boolean player1Turn = true;

        while (!isGameOver()) {
            if (player1Turn) {
                System.out.println("Player 1's turn:");
                printGrid(player1TrackingGrid);
                playerTurn(player2Grid, player1TrackingGrid);
            } else {
                System.out.println("Player 2's turn:");
                printGrid(player2TrackingGrid);
                playerTurn(player1Grid, player2TrackingGrid);
            }
            player1Turn = !player1Turn;
        }

        if (allShipsSunk(player1Grid)) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("Player 1 wins!");
        }
        System.out.println("Game Over!");
    }

    static void initializeGrid(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = WATER_CHAR;
            }
        }
    }

    static void placeShips(char[][] grid) {
        for (int shipSize : shipSizes) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(GRID_SIZE);
                int col = random.nextInt(GRID_SIZE);
                boolean horizontal = random.nextBoolean();

                if (canPlaceShip(grid, row, col, shipSize, horizontal)) {
                    for (int i = 0; i < shipSize; i++) {
                        if (horizontal) {
                            grid[row][col + i] = SHIP_CHAR;
                        } else {
                            grid[row + i][col] = SHIP_CHAR;
                        }
                    }
                    placed = true;
                }
            }
        }
    }

    static boolean canPlaceShip(char[][] grid, int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > GRID_SIZE) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row][col + i] != WATER_CHAR) return false;
            }
        } else {
            if (row + size > GRID_SIZE) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row + i][col] != WATER_CHAR) return false;
            }
        }
        return true;
    }

    static void playerTurn(char[][] opponentGrid, char[][] trackingGrid) {
        boolean validShot = false;
        while (!validShot) {
            System.out.print("Enter coordinates to attack (e.g., A5): ");
            String input = scanner.nextLine().toUpperCase();

            if (isValidInput(input)) {
                int row = input.charAt(0) - 'A';
                int col = Integer.parseInt(input.substring(1)) - 1;

                if (trackingGrid[row][col] != WATER_CHAR && trackingGrid[row][col] != SHIP_CHAR) {
                    System.out.println("You already attacked this location. Try again.");
                } else {
                    validShot = true;
                    if (opponentGrid[row][col] == SHIP_CHAR) {
                        System.out.println("Hit!");
                        trackingGrid[row][col] = HIT_CHAR;
                        opponentGrid[row][col] = HIT_CHAR;
                    } else {
                        System.out.println("Miss!");
                        trackingGrid[row][col] = MISS_CHAR;
                        opponentGrid[row][col] = WATER_CHAR;
                    }
                }
            } else {
                System.out.println("Invalid input format. Please use format like A1, B2, ..., J10.");
            }
        }
    }

    static boolean isGameOver() {
        return allShipsSunk(player1Grid) || allShipsSunk(player2Grid);
    }

    static boolean allShipsSunk(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == SHIP_CHAR) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean isValidInput(String input) {
        if (input == null || input.length() < 2 || input.length() > 3) {
            return false;
        }
        char rowChar = input.charAt(0);
        if (rowChar < 'A' || rowChar > 'J') {
            return false;
        }
        String colStr = input.substring(1);

        int col = Integer.parseInt(colStr);
        if (col < 1 || col > 10) {
            return false;
        }
        return true;
    }

    static void printGrid(char[][] grid) {
        System.out.print("  ");
        for (int i = 1; i <= GRID_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print((char)('A' + i) + " ");
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}