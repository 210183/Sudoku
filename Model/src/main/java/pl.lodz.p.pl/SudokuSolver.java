package pl.lodz.p.pl;

import pl.lodz.p.pl.Exceptions.BacktrackingSolverException;

public interface SudokuSolver {
     boolean solve(SudokuBoard board) throws BacktrackingSolverException;
}
