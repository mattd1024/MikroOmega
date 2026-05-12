package minesweeper.model;

public enum Difficulty {
    EASY("Easy", 9, 9, 10),
    MEDIUM("Medium", 16, 16, 40),
    HARD("Hard", 16, 30, 99);

    private final String label;
    private final int rows;
    private final int cols;
    private final int totalMines;

    Difficulty(String label, int rows, int cols, int mines) {
        this.label = label;
        this.rows = rows;
        this.cols = cols;
        this.totalMines = mines;
    }

    public String getLabel() {
        return label;
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
}
