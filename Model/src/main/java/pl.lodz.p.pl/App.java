package pl.lodz.p.pl;

public class App {
    public static void main(final String[] args) {
        System.out.println("Sudoku Board");

        try {
            SudokuBoard someBoard = new SudokuBoard();
            BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
            solver.solve(someBoard);
            someBoard.consoleShow();
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

    }
}
