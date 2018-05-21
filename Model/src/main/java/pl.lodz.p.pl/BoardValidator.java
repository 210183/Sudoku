package pl.lodz.p.pl;

import java.util.Arrays;

import static pl.lodz.p.pl.SudokuConstants.boardSize;

public class BoardValidator {
    public static boolean validate(SudokuBoard sudoku) {
        boolean[] isNumberAlreadyFound = new boolean[boardSize + 1]; // from 0 - 9, cause zero means not filled yet
        //region check rows
        for (int line = 0; line < boardSize; line++) {
            Arrays.fill(isNumberAlreadyFound, false);
            for (int cell = 0; cell < boardSize; cell++) {
                if (sudoku.getBoard().get(line).get(cell).getValue() != 0) {
                    if (isNumberAlreadyFound[sudoku.getBoard().get(line).get(cell).getValue()]) {
                        return false;
                    } else {
                        isNumberAlreadyFound[sudoku.getBoard().get(line).get(cell).getValue()] = true;
                    }
                }
            }
        }
        //endregion
        //region check column
        for (int column = 0; column < boardSize; column++) {
            Arrays.fill(isNumberAlreadyFound, false);
            for (int cell = 0; cell < boardSize; cell++) {
                if (sudoku.getBoard().get(cell).get(column).getValue() != 0) {
                    if (isNumberAlreadyFound[sudoku.getBoard().get(cell).get(column).getValue()]) {
                        return false;
                    } else {
                        isNumberAlreadyFound[sudoku.getBoard().get(cell).get(column).getValue()] = true;
                    }
                }
            }
        }
        //endregion
        //region check squares
        for (int i = 0; i < boardSize; i += 3) {
            for (int j = 0; j < boardSize; j += 3) {
                if (!checkSquare(sudoku, i, j)) {
                    return false;
                }
            }
        }
        //endregion
        return true;
    }

    private static boolean checkSquare(SudokuBoard sudoku, int startingRowIndex, int startingColIndex) {
        boolean[] isNumberAlreadyFound = new boolean[boardSize + 1];
        for (int i = startingRowIndex; i < startingRowIndex + 3; i++) {
            for (int j = startingColIndex; j < startingColIndex + 3; j++) {
                if (sudoku.getBoard().get(i).get(j).getValue() != 0) {
                    if (isNumberAlreadyFound[sudoku.getBoard().get(i).get(j).getValue()]) {
                        return false;
                    } else {
                        isNumberAlreadyFound[sudoku.getBoard().get(i).get(j).getValue()] = true;
                    }
                }
            }
        }
        return true;
    }
}
