package pl.lodz.p.pl.Dao;

import pl.lodz.p.pl.SudokuBoard;

public class SudokuBoardDaoFactory {
    public FileSudokuBoardDao getFileDao(final String fileName) {
        return new FileSudokuBoardDao(fileName);
    }
}
