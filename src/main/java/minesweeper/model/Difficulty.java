package minesweeper.model;

public class Difficulty {
    public static final Difficulty EASY = new Difficulty("Easy", 9, 9, 10);
    public static final Difficulty MEDIUM = new Difficulty("Medium", 16, 16, 40);
    public static final Difficulty HARD = new Difficulty("Hard", 16, 30, 99);

    private final String label;
    private String description;
    private final int rows;
    private final int cols;
    private final int totalMines;

    public Difficulty(String label, int rows, int cols, int mines) {
        this.label = label;
        this.description = label;
        this.rows = rows;
        this.cols = cols;
        this.totalMines = mines;
    }

    public boolean isValid() {
        if (rows >= 2 && cols >= 2 && totalMines > 0 && totalMines < rows * cols - 9) {
            return true;
        }
        return false;
    }

    public String getValidationError() {
        if (rows < 2 || cols < 2) {
            return "Board must be atleast 2x2";
        }
        if (totalMines < 1) {
            return "Must have at least one mine";
        }
        if (totalMines >= rows * cols - 9) {
            return "Too many mines for this board size";
        }
        return null;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
