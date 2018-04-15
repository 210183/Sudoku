package test.pl.lodz.p.pl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.lodz.p.pl.SudokuConstants.boardSize;

import org.junit.Test;

import pl.lodz.p.pl.SudokuBoard;
import pl.lodz.p.pl.SudokuField;
import pl.lodz.p.pl.SudokuLine;

public class EqualsHashToStringTest {

    @Test
    public void Field_Test()
    {
        SudokuField field = new SudokuField(3);
        SudokuField sameField = new SudokuField(3);
        System.out.println(field.toString());
        System.out.println(field.hashCode());
        System.out.println(sameField.hashCode());
        System.out.println(field.equals(sameField));
        assertEquals(field.toString(), "SudokuField{value=3}");
        assertTrue(field.hashCode() == 34);
        assertTrue(field.equals(sameField) == true);
    }

    @Test
    public void Board_Test()
    {
        SudokuBoard board = new SudokuBoard();
        SudokuBoard sameBoard = new SudokuBoard();
        System.out.println(board.toString());
        System.out.println(board.hashCode());
        System.out.println(sameBoard.hashCode());
        System.out.println(board.equals(sameBoard));
        assertTrue(board.equals(board) == true);
        assertTrue(board.equals(sameBoard) == false);
    }

    @Test
    public void Line_Test()
    {
        SudokuLine line = new SudokuLine();
        SudokuLine sameLine = new SudokuLine();
        System.out.println(line.toString());
        System.out.println(line.hashCode());
        System.out.println(sameLine.hashCode());
        System.out.println(line.equals(sameLine));
        assertTrue(line.equals(sameLine) == true);
    }
}
