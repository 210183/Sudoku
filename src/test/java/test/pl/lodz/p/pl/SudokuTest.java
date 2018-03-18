package test.pl.lodz.p.pl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;
import pl.lodz.p.pl.BacktrackingSudokuSolver;
import pl.lodz.p.pl.SudokuBoard;

import java.util.Arrays;

public class SudokuTest {
    private static final int boardWidth = 9;

    @Test
    public void CreateProperSudokuBoard_Test() {
        pl.lodz.p.pl.SudokuBoard firstBoard = new pl.lodz.p.pl.SudokuBoard();
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        solver.solve(firstBoard);
        validate(firstBoard);

        pl.lodz.p.pl.SudokuBoard secondBoard = new pl.lodz.p.pl.SudokuBoard();
        solver.solve(secondBoard);
        validate(secondBoard);
        assert (!Equals(firstBoard.getBoard(), secondBoard.getBoard())); //should randomize two different boards
    }

    private boolean validate(SudokuBoard sudoku) {
        boolean[] isNumberAlreadyFound = new boolean[boardWidth + 1]; // from 0 - 9, cause zero means not filled yet
        //region check rows
        for (int line = 0; line < boardWidth; line++) {
            Arrays.fill(isNumberAlreadyFound, false);
            for (int cell = 0; cell < boardWidth; cell++) {
                if (sudoku.getBoard()[line][cell] != 0) {
                    if (isNumberAlreadyFound[sudoku.getBoard()[line][cell]]) {
                        return false;
                    } else {
                        isNumberAlreadyFound[sudoku.getBoard()[line][cell]] = true;
                    }
                }
            }

        }
        //endregion
        //region check column
        for (int column = 0; column < boardWidth; column++) {
            Arrays.fill(isNumberAlreadyFound, false);
            for (int cell = 0; cell < boardWidth; cell++) {
                if (sudoku.getBoard()[cell][column] != 0) {
                    if (isNumberAlreadyFound[sudoku.getBoard()[cell][column]]) {
                        return false;
                    } else {
                        isNumberAlreadyFound[sudoku.getBoard()[cell][column]] = true;
                    }
                }
            }

        }
        //endregion
        //region check squares
        for (int i = 0; i < boardWidth; i += 3) {
            for (int j = 0; j < boardWidth; j += 3) {
                if (!checkSquare(sudoku, i, j)) {
                    return false;
                }
            }
        }
        //endregion
        return true;
    }

    private boolean checkSquare(SudokuBoard sudoku, int startingRowIndex, int startingColIndex) {
        boolean[] isNumberAlreadyFound = new boolean[boardWidth + 1];
        for (int i = startingRowIndex; i < startingRowIndex + 3; i++) {
            for (int j = startingColIndex; j < startingColIndex + 3; j++) {
                if (sudoku.getBoard()[i][j] != 0) {
                    if (isNumberAlreadyFound[sudoku.getBoard()[i][j]]) {
                        return false;
                    } else {
                        isNumberAlreadyFound[sudoku.getBoard()[i][j]] = true;
                    }
                }
            }
        }
        return true;
    }

    private boolean Equals(int[][] firstBoard, int[][] secondBoard) {
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (firstBoard[i][j] != secondBoard[i][j])
                    return false;
            }
        }
        return true;
    }
}

