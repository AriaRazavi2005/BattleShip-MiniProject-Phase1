import java.util.Scanner;
import java.util.Random;

// Aria Razavio
/**
 The BattleShip class manages the gameplay of the Battleship game between two players.
 It includes methods to manage grids, turns, and check the game status.
 */
public class maine {

    static final int GRID_SIZE = 10;
    static final char WATER = '~';
    static final char SHIP = 'S';
    static final char HIT = 'X';
    static final char MISS = 'O';
    static final int SHIP_COUNT = 5;

    static char[][] player1Grid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2Grid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player1TrackingGrid = new char[GRID_SIZE][GRID_SIZE];
    static char[][] player2TrackingGrid = new char[GRID_SIZE][GRID_SIZE];

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

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
        System.out.println("Game Over!");
    }

    static void initializeGrid(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = WATER;
            }
        }
    }

    static void placeShips(char[][] grid) {
        int shipsPlaced = 0;
        while (shipsPlaced < SHIP_COUNT) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);
            if (grid[row][col] == WATER) {
                grid[row][col] = SHIP;
                shipsPlaced++;
            }
        }
    }

    static boolean canPlaceShip(char[][] grid, int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > GRID_SIZE) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row][col + i] != WATER) return false;
            }
        } else {
            if (row + size > GRID_SIZE) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row + i][col] != WATER) return false;
            }
        }
        return true;
    }

    static void playerTurn(char[][] opponentGrid, char[][] trackingGrid) {
        int row, col;
        while (true) {
            System.out.print("Enter coordinates to fire (row and column): ");
            row = scanner.nextInt();
            col = scanner.nextInt();
            if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE && trackingGrid[row][col] == WATER) {
                break;
            }
            System.out.println("Invalid coordinates or already shot there. Try again.");
        }

        if (opponentGrid[row][col] == SHIP) {
            System.out.println("Hit!");
            opponentGrid[row][col] = HIT;
            trackingGrid[row][col] = HIT;
        } else {
            System.out.println("Miss!");
            opponentGrid[row][col] = MISS;
            trackingGrid[row][col] = MISS;
        }
    }

    static boolean isGameOver() {
        return allShipsSunk(player1Grid) || allShipsSunk(player2Grid);
    }

    static boolean allShipsSunk(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == SHIP) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean isValidInput(String input) {
        return input.matches("[A-J][0-9]");
    }

    static void printGrid(char[][] grid) {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
