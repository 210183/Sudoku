package test.pl.lodz.p.pl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.lodz.p.pl.SudokuConstants.boardSize;
import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.lodz.p.pl.Exceptions.BoardException;
import pl.lodz.p.pl.SudokuBoard;

import java.util.Arrays;
import java.util.List;

public class SudokuBoardTest {

    @Test
    public void SetInvalidValue_ShouldThrowBoardExceptionWithInnerException() {
        SudokuBoard board = new SudokuBoard();
        BoardException ex =  Assertions.assertThrows(
                BoardException.class,
                ()-> board.setBoardValueAt(0,0,boardSize+1));
        Assert.assertThat
    }
}

