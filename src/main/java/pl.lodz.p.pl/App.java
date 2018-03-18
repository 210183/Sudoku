package pl.lodz.p.pl;

public class App {
    public static void main(final String[] args) {
        System.out.println("Sudoku Board");
        boolean result = false;
        SudokuBoard someBoard;

        try {
            someBoard = new SudokuBoard();
            result = someBoard.fill();
            someBoard.consoleShow();

            System.out.println("result: " + result + " ");
        } catch (Exception e) {

            System.out.println("result: " + result + " " + e.getMessage());
        }

    }
}
