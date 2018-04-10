package test.pl.lodz.p.pl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.lodz.p.pl.SudokuConstants.boardSize;

import org.junit.Test;
import pl.lodz.p.pl.*;

import java.util.Arrays;
import java.util.List;

public class SudokuTest {
    private static final int boardWidth = 9;

    @Test
    public void CreateProperSudokuBoard_Test() {
        SudokuBoard firstBoard = new SudokuBoard();
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        solver.solve(firstBoard);
        validate(firstBoard);

        SudokuBoard secondBoard = new SudokuBoard();
        solver.solve(secondBoard);
        validate(secondBoard);
        assert (!Equals(firstBoard.getBoard(), secondBoard.getBoard())); //should randomize two different boards
    }

    @Test
    public void BoardConsoleShow_ShouldDisplayCorrectBoard_Test()
    {
        SudokuBoard firstBoard = new SudokuBoard();
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        solver.solve(firstBoard);
        firstBoard.consoleShow();
        //if execution reached assert below, assuming it;s ok
        assertTrue(true);
    }

    @Test
    public void SudokuLine_getValues_Test()
    {
        int valueToSet = 5;
        SudokuLine line = new SudokuLine();
        line.setValue(2, valueToSet);
        List<SudokuField> fields = line.getValues();
        //assertEquals(fields.get(2).getValue(), valueToSet);
        //assertNotEquals(fields, line.getValues());
    }

    @Test(expected = IllegalArgumentException.class)
    public void SudokuField_setBadValue_Test()
    {
      SudokuField field = new SudokuField(0);
      field.setValue(10);
    }
    @Test(expected = IllegalArgumentException.class)
    public void SudokuField_ConstructWithBadValue_Test()
    {
        SudokuField field = new SudokuField(-2);
    }

    private boolean validate(SudokuBoard sudoku) {
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

    private boolean checkSquare(SudokuBoard sudoku, int startingRowIndex, int startingColIndex) {
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

    /*
    Checks underlying int values in SudokuField (board[x][y].getValue())
*/
    private boolean Equals(List<FixedList> firstBoard, List<FixedList> secondBoard) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (firstBoard.get(i).get(j).getValue() != secondBoard.get(i).get(j).getValue())
                    return false;
            }
        }
        return true;
    }
}

