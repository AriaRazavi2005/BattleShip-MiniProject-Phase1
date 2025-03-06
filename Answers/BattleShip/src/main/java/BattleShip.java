import java.util.Random;
import java.util.Scanner;

public class BattleShip {
    static final int GRID_SIZE = 10;
    static final char WATER = '~';
    static final char SHIP = 'P';
    static final char HIT = 'X';
    static final char MISS = 'O';

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
                System.out.println("Player One Turn:");
                printGrid(player1TrackingGrid);
                playerTurn(player2Grid, player1TrackingGrid);
            } else {
                System.out.println("Player Two Turn:");
                printGrid(player2TrackingGrid);
                playerTurn(player1Grid, player2TrackingGrid);
            }
            player1Turn = !player1Turn;
        }

        System.out.println("The game is over!");
    }

    static void initializeGrid(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++)
            for (int j = 0; j < GRID_SIZE; j++)
                grid[i][j] = WATER;
    }

    static void placeShips(char[][] grid) {
        int shipsPlaced = 5;
        while (shipsPlaced > 0) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);
            if (grid[row][col] == WATER) {
                grid[row][col] = SHIP;
                shipsPlaced--;
            }
        }
    }

    static void playerTurn(char[][] opponentGrid, char[][] trackingGrid) {
        System.out.println("Enter Target : ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE) {
            System.out.println("Invalid input!  Try again.");
            return;
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
        for (int i = 0; i < GRID_SIZE; i++)
            for (int j = 0; j < GRID_SIZE; j++)
                if (grid[i][j] == SHIP)
                    return false;
        return true;
    }

    static void printGrid(char[][] grid) {
        System.out.print("  ");
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}