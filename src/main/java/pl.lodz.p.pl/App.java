package pl.lodz.p.pl;

public class App {
    public static void main(final String[] args) {
        System.out.println("Sudoku Board");
        boolean result = false;
        SudokuBoard someBoard;
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();

        try {
            someBoard = new SudokuBoard();

            result = solver.solve(someBoard);
            someBoard.consoleShow();

            System.out.println("result: " + result + " ");
        } catch (Exception e) {

            System.out.println("result: " + result + " " + e.getMessage());
        }

    }
}
