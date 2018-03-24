package pl.lodz.p.pl;

import javax.swing.*;
import java.util.*;

import static pl.lodz.p.pl.SudokuConstants.*;

public class SudokuBoard {

    private SudokuField[][] board = new SudokuField[boardSize][boardSize];
    private SudokuRow[] rows = new SudokuRow[boardSize];
    private SudokuColumn[] columns = new SudokuColumn[boardSize];
    private SudokuBox[] boxes = new SudokuBox[boardSize];

    public SudokuBoard() {
        // init SudokuField
        for (int i = 0; i < boardSize; i++) {
            rows[i] = new SudokuRow();
            columns[i] = new SudokuColumn();
            boxes[i] = new SudokuBox();
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        //
        List<SudokuField> rowValues = new ArrayList<>();
        for (int i = 1; i < boardSize + 1; i++) {
            rowValues.add(new SudokuField(i));
        }
        Collections.shuffle(rowValues);
        SudokuField[] row = new SudokuField[boardSize];
        for (int i = 0; i < boardSize; i++) {
            board[0][i] = rowValues.get(i);
        }
    }

    public SudokuField[][] getBoard() {
        SudokuField[][] copy = new SudokuField[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    public void setBoardValueAt(int rowIndex, int columnIndex, int newValue) throws IllegalArgumentException {
        if (isFieldValueInBounds(newValue)) {
            if (isIndexInBounds(rowIndex) && isIndexInBounds(columnIndex)) {
                board[rowIndex][columnIndex].setValue(newValue);
                rows[rowIndex].setValue(columnIndex, newValue);
                columns[columnIndex].setValue(rowIndex, newValue);
                setBoxValueForIndexes(rowIndex, columnIndex, newValue);
            } else {
                throw new IllegalArgumentException("Bad index");
            }
        } else {
            throw new IllegalArgumentException("Wrong value to set");
        }
    }

    public void consoleShow() {
        int rowCounter = 0;
        int colCounter = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (colCounter == 2) {
                    System.out.print(board[i][j].getValue() + " | ");
                    colCounter = 0;
                } else {
                    System.out.print(board[i][j].getValue() + " ");
                    colCounter++;
                }
            }
            if (rowCounter == 2) {
                System.out.print("\n");
                for (int k = 0; k < 12; k++) {
                    System.out.print("- ");
                }
                System.out.print("\n");
                rowCounter = 0;
            } else {
                System.out.print("\n");
                rowCounter++;
            }
        }
    }

    public boolean isValid(final BoardIndex boardIndex, int value) {
        int row = boardIndex.row;
        int col = boardIndex.col;
        if (columns[col].verify(value)) {
            if (rows[row].verify(value)) {
                int boxIndex = getBoxNumberForIndexes(row, col);
                if (boxes[boxIndex].verify(value)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static class BoardIndex {

        int row, col;

        public BoardIndex(int row, int col) {
            super();
            this.row = row;
            this.col = col;
        }

    }

    public BoardIndex getNextBoardIndex(final BoardIndex cur) {

        int row = cur.row;
        int col = cur.col;

        col++;
        if (col >= boardSize) {
            col = 0;
            row++;
        }

        if (row >= boardSize) {
            return null;
        }

        BoardIndex next = new BoardIndex(row, col);
        return next;
    }

    public BoxIndex getBoxIndexForIndexes(int rowIndex, int colIndex) {
        int boxRowIndex = rowIndex % 3;
        int boxColumnIndex = colIndex % 3;
        return new BoxIndex(boxRowIndex, boxColumnIndex);
    }

    private void setBoxValueForIndexes(int rowIndex, int colIndex, int value) {
        int boxNumber = getBoxNumberForIndexes(rowIndex, colIndex);
        BoxIndex boxIndex = getBoxIndexForIndexes(rowIndex, colIndex);
        boxes[boxNumber].setValue(boxIndex.getRow(), boxIndex.getCol(), value);
    }

    public int getBoxNumberForIndexes(int rowIndex, int colIndex) {
        return colIndex / 3 + (rowIndex / 3) * 3;
    }
}
