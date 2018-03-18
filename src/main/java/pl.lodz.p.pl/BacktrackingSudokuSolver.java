package pl.lodz.p.pl;

import static pl.lodz.p.pl.SudokuBoard.boardWidth;

public class BacktrackingSudokuSolver implements SudokuSolver {


    public boolean solve(final SudokuBoard board) {
        return solve(board, new SudokuBoard.BoardElement(0, 0));
    }

    private boolean solve(final SudokuBoard board, final SudokuBoard.BoardElement cur) {

        if (cur == null) {
            return true;
        }

        //if not zero, then already filled
        if (board.getBoard()[cur.row][cur.col] != 0) {
            return solve(board, board.getNextBoardElement(cur));
        }

        for (int i = 1; i <= 9; i++) {

            boolean valid = board.isValid(cur, i);

            if (!valid) { // i not valid for this cell, try other values
                continue;
            }

            board.setBoardValuesAt(cur.row, cur.col, i);
            boolean solved = solve(board, board.getNextBoardElement(cur));
            if (solved) {
                return true;
            } else {
                board.setBoardValuesAt(cur.row, cur.col, 0); // reset
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
