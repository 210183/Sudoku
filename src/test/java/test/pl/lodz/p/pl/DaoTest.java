package test.pl.lodz.p.pl;

import org.junit.Test;
import pl.lodz.p.pl.Dao.FileSudokuBoardDao;
import pl.lodz.p.pl.Dao.SudokuBoardDaoFactory;
import pl.lodz.p.pl.SudokuBoard;

import static org.junit.Assert.assertTrue;

public class DaoTest {

    @Test
    public void SerializeTest() {
        SudokuBoard board = new SudokuBoard();
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        try
        {
            try(FileSudokuBoardDao dao =  factory.getFileDao("C:\\Users\\Lola\\Desktop\\sudoku.txt"))
            {
                dao.write(board);
                SudokuBoard readBoard = dao.read();
                readBoard.consoleShow();
                assertTrue(board.equals(readBoard));
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
