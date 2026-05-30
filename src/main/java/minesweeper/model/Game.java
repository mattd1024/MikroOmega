package minesweeper.model;

import java.util.Random;

public class Game {
    private Difficulty difficulty;
    private final Cell[][] cells;
    private final int rows;
    private final int cols;
    private final int totalMines;
    private int remainingFlags;
    private boolean isFirstClick;

    public Game(Difficulty chosenDifficulty) {
        this.difficulty = chosenDifficulty;
        this.rows = difficulty.getRows();
        this.cols = difficulty.getCols();
        this.totalMines = difficulty.getTotalMines();
        this.isFirstClick = true;
        remainingFlags = totalMines;

        // Create empty board
        this.cells = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                cells[r][c] = new Cell();
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
            if (!cells[r][c].isMine() && (Math.abs(r - safeR) > 1 || Math.abs(c - safeC) > 1)) {
                cells[r][c].setMine(true);
                placed++;
            }
        }

        // Calculate adjacent mines to every non-mine cell
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!cells[r][c].isMine()) {
                    cells[r][c].setAdjacentMines(countAdjacentMines(r, c));
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
                int nc = c + dc;
                if (inBounds(nr, nc) && cells[nr][nc].isMine()) {
                    adjacentMines++;
                }
            }
        }
        return adjacentMines;
    }

    /**
     * Recursive method for revealing all empty cells around an empty cell
     */
    public void floodReveal(int r, int c) {
        // Check if the coordinates are valid
        if (!inBounds(r, c)) {
            return;
        }

        // Get the respective cell
        Cell cell = getCells()[r][c];

        // If the cell is revealed or flagged, skip
        if (cell.isRevealed() || cell.isFlagged()) {
            return;
        }

        cell.setRevealed(true);

        // Go over surrounding cells without choosing the same one
        if (cell.getAdjacentMines() == 0) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (!(dr == 0 && dc == 0)) { // Dont want to call floodReveal on the same cell indefinitely
                        floodReveal(r + dr, c + dc);
                    }
                }
            }// Only flood reveal if there are no surrounding mines
        }

    }

    /**
     * Checks if the given coordinates r and c are valid for our board
     * @return true = valid, false = invalid
     */
    public boolean inBounds(int r, int c) {
        if (r >= 0 && r < rows && c >= 0 && c < cols) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if the game is won
     */
    public boolean isWon() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!cells[r][c].isMine() && !cells[r][c].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Reveals all revealable cells
     */
    public void revealAll() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!cells[r][c].isFlagged() &&  !cells[r][c].isRevealed()) {
                    cells[r][c].setRevealed(true);
                }
            }
        }
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Cell[][] getCells() {
        return cells;
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

    public void decrementRemainingFlags() {
        remainingFlags--;
    }

    public void incrementRemainingFlags() {
        remainingFlags++;
    }

    public int getRemainingFlags() {
        return remainingFlags;
    }
}
