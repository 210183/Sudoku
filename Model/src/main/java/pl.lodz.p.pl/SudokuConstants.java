package pl.lodz.p.pl;

public abstract class SudokuConstants {
    public static final int boardSize = 9;

    public static boolean isIndexInBounds(int value) {
        return value >= 0 && value < boardSize;
    }

    public static boolean isFieldValueInBounds(int value) {
        return value >= 0 && value < boardSize + 1;
    }
}
