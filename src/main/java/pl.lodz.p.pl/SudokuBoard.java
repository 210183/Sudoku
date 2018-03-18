package pl.lodz.p.pl;

import java.util.*;

public class SudokuBoard {
    public static final int boardWidth = 9;
    private int[][] board = new int[boardWidth][boardWidth];
    //private Integer[] cellOrder = new Integer[81];

    public SudokuBoard() {
        List<Integer> rowValues = new ArrayList<>();
        for (int i = 1; i < boardWidth + 1; i++) {
            rowValues.add(i);
        }
        Collections.shuffle(rowValues);
        int[] row = rowValues.stream()
                .mapToInt(Integer::intValue)
                .toArray();
        for (int i = 0; i < boardWidth; i++) {
            board[0][i] = row[i];
        }

    }

    public int[][] getBoard() {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy[i][j] = board[i][j];
            }
        }

        return copy;
    }

    public void setBoardValuesAt(final IntPair index, int newValue) throws IllegalArgumentException {
        if (newValue > 0 && newValue < boardWidth + 1) {
            if (index.First >= 0 && index.First < boardWidth && index.Second >= 0 && index.Second < 10) {
                board[index.First][index.Second] = newValue;
            } else {
                throw new IllegalArgumentException("Bad index");
            }
        } else {
            throw new IllegalArgumentException("Wrong value to set");
        }

    }

    public void setBoardValuesAt(int rowIndex, int columnIndex, int newValue) throws IllegalArgumentException {
        if (newValue >= 0 && newValue < boardWidth + 1) {
            if (rowIndex >= 0 && rowIndex < boardWidth && columnIndex >= 0 && columnIndex < boardWidth) {
                board[rowIndex][columnIndex] = newValue;
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
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (colCounter == 2) {
                    System.out.print(board[i][j] + " | ");
                    colCounter = 0;
                } else {
                    System.out.print(board[i][j] + " ");
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

    public boolean isValid(final BoardElement boardElement, int value) {
        //region columns
        for (int c = 0; c < 9; c++) {
            if (board[boardElement.row][c] == value) {
                return false;
            }
        }
        //endregion
        // region rows
        for (int r = 0; r < 9; r++) {
            if (board[r][boardElement.col] == value) {
                return false;
            }
        }
        //endregion
        //region squares
        int x1 = 3 * (boardElement.row / 3);
        int y1 = 3 * (boardElement.col / 3);
        int x2 = x1 + 2;
        int y2 = y1 + 2;

        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                if (board[x][y] == value) {
                    return false;
                }
            }
        }
        //endregion
        // if reached here, value is correct
        return true;
    }

    public static class BoardElement {

        int row, col;

        public BoardElement(int row, int col) {
            super();
            this.row = row;
            this.col = col;
        }

    }

    public SudokuBoard.BoardElement getNextBoardElement(final SudokuBoard.BoardElement cur) {

        int row = cur.row;
        int col = cur.col;

        col++;
        if (col > 8) {
            col = 0;
            row++;
        }

        if (row > 8) {
            return null;
        }

        SudokuBoard.BoardElement next = new SudokuBoard.BoardElement(row, col);
        return next;
    }
}
