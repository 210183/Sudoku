package pl.lodz.p.pl;

import java.util.*;

public class SudokuBoard {
    private static final int boardWidth = 9;
    private int[][] board = new int[boardWidth][boardWidth];
    //private Integer[] cellOrder = new Integer[81];

    public SudokuBoard() {
        List<Integer> rowValues = new ArrayList<Integer>();
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

    public boolean fill() {
        return solve(new BoardElement(0, 0));
    }

    private static class BoardElement {

        int row, col;

        public BoardElement(int row, int col) {
            super();
            this.row = row;
            this.col = col;
        }

    }

    private boolean isValid(final BoardElement boardElement, int value) {
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

    private BoardElement getNextBoardElement(final BoardElement cur) {

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

        BoardElement next = new BoardElement(row, col);
        return next;
    }

    private boolean solve(final BoardElement cur) {

        if (cur == null) {
            return true;
        }

        //if not zero, then already filled
        if (board[cur.row][cur.col] != 0) {
            return solve(getNextBoardElement(cur));
        }

        for (int i = 1; i <= 9; i++) {

            boolean valid = isValid(cur, i);

            if (!valid) { // i not valid for this cell, try other values
                continue;
            }

            board[cur.row][cur.col] = i;
            boolean solved = solve(getNextBoardElement(cur));
            if (solved) {
                return true;
            } else {
                board[cur.row][cur.col] = 0; // reset
            }
            // continue with other values
        }
        return false;
    }

    private IntPair convertIndex(final Integer oneDIndex) {
        IntPair pair = new IntPair();
        pair.First = oneDIndex / boardWidth;
        pair.Second = oneDIndex % boardWidth;
        return pair;
    }
}
