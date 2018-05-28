package pl.lodz.p.pl;

import pl.lodz.p.pl.Exceptions.BacktrackingSolverException;
import pl.lodz.p.pl.Exceptions.InvalidIndexException;
import pl.lodz.p.pl.Exceptions.InvalidValueException;

import static pl.lodz.p.pl.SudokuConstants.boardSize;

public class BacktrackingSudokuSolver implements SudokuSolver {


    public boolean solve(final SudokuBoard board) throws BacktrackingSolverException {
        return solve(board, new SudokuBoard.BoardIndex(0, 0));
    }

    private boolean solve(final SudokuBoard board, final SudokuBoard.BoardIndex cur) throws BacktrackingSolverException {

        if (cur == null) {
            return true;
        }

        //if not zero, then already filled
        if (board.getBoard().get(cur.row).get(cur.col).getValue() != 0) {
            return solve(board, board.getNextBoardIndex(cur));
        }

        for (int i = 1; i <= 9; i++) {
            boolean valid = false;
            try{
             valid = board.isValid(cur, i);
            }catch(InvalidIndexException e){
                throw new BacktrackingSolverException("Board is invalid", e);
            }

            if (!valid) { // i not valid for this cell, try other values
                continue;
            }

            try{
                board.setBoardValueAt(cur.row, cur.col, i);
            }catch(IllegalArgumentException e){
                throw new BacktrackingSolverException("Cannot set value", e);
            }
            boolean solved = solve(board, board.getNextBoardIndex(cur));
            if (solved) {
                return true;
            } else {
                try{
                    board.setBoardValueAt(cur.row, cur.col, 0); // reset}
                }catch(IllegalArgumentException e){
                    throw new BacktrackingSolverException("Cannot set value", e);
                }

            }
            // continue with other values
        }
        return false;
    }
}
