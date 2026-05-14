package minesweeper.model;

import java.util.Random;

public class Game {
    private Difficulty difficulty;
    private final Cell[][] board;
    private final int rows;
    private final int cols;
    private final int totalMines;
    private boolean isFirstClick;

    public Game(Difficulty chosenDifficulty) {
        this.difficulty = chosenDifficulty;
        this.rows = difficulty.getRows();
        this.cols = difficulty.getCols();
        this.totalMines = difficulty.getTotalMines();
        this.isFirstClick = true;

        // Create empty board
        this.board = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = new Cell();
            }
        }
    }

    /**
     * Places mines around the field randomly. Keeps a safe zone at the first click.
     * Calculates the adjacent mines to every non-mine cell.
     * safeR = Rows coordinate of the first player click
     * safeC = Columns coordinate of the first player click
     */
    public void placeMines(int safeR, int safeC) {
        // Place mines around the board
        Random rng = new Random();
        int placed = 0;
        while (placed < totalMines) {
            int r = rng.nextInt(rows);
            int c = rng.nextInt(cols);
            // If the randomly selected cell is not a mine and is not inside a safe zone, place a mine in it
            if (!board[r][c].isMine() && (Math.abs(r - safeR) > 1 || Math.abs(c - safeC) > 1)) {
                board[r][c].setMine(true);
                placed++;
            }
        }

        // TODO finish calculating adjacent mines for empty cells
        // Calculate adjacent mines to every non-mine cell
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!board[r][c].isMine()) {
                    board[r][c].setAdjacentMines(countAdjacentMines(r, c));
                }
            }
        }
    }

    /**
     * Calculates adjacent mines for a given cell.
     * int r = row
     * int c = col
     */
    public int countAdjacentMines(int r, int c) {
        int adjacentMines = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = r + dr;
                int nc = r + dc;
                if (inBounds(nr, nc) && board[nr][nc].isMine()) {
                    adjacentMines++;
                }
            }
        }
        return adjacentMines;
    }

    /**
     * Checks if the given coordinates r and c are valid for our board
     * @return true = valid, false = invalid
     */
    // TODO check if the inbounds check is correct
    public boolean inBounds(int r, int c) {
        if ((r > 0 && r < rows) && (c > 0 && c < cols)) {
            return true;
        } else {
            return false;
        }
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public boolean isFirstClick() {
        return isFirstClick;
    }

    public void setFirstClick(boolean firstClick) {
        isFirstClick = firstClick;
    }
}
