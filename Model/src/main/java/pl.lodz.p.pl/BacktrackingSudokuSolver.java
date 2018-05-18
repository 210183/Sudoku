package pl.lodz.p.pl;

import static pl.lodz.p.pl.SudokuConstants.boardSize;

public class BacktrackingSudokuSolver implements SudokuSolver {


    public boolean solve(final SudokuBoard board) {
        return solve(board, new SudokuBoard.BoardIndex(0, 0));
    }

    private boolean solve(final SudokuBoard board, final SudokuBoard.BoardIndex cur) {

        if (cur == null) {
            return true;
        }

        //if not zero, then already filled
        if (board.getBoard().get(cur.row).get(cur.col).getValue() != 0) {
            return solve(board, board.getNextBoardIndex(cur));
        }

        for (int i = 1; i <= 9; i++) {

            boolean valid = board.isValid(cur, i);

            if (!valid) { // i not valid for this cell, try other values
                continue;
            }

            board.setBoardValueAt(cur.row, cur.col, i);
            boolean solved = solve(board, board.getNextBoardIndex(cur));
            if (solved) {
                return true;
            } else {
                board.setBoardValueAt(cur.row, cur.col, 0); // reset
            }
            // continue with other values
        }
        return false;
    }
}
