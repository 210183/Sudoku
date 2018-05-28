package test.pl.lodz.p.pl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.lodz.p.pl.HelperMethods.LogException;
import static pl.lodz.p.pl.SudokuConstants.boardSize;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.lodz.p.pl.Exceptions.BoardException;
import pl.lodz.p.pl.Exceptions.InvalidIndexException;
import pl.lodz.p.pl.Exceptions.InvalidValueException;
import pl.lodz.p.pl.SudokuBoard;

import java.util.Arrays;
import java.util.List;

public class SudokuBoardTest {

    @Test
    public void SetInvalidValue_ShouldThrowBoardExceptionWithInnerException() {
        System.setProperty("log4j.configurationFile","./log4j2.xml");
        Logger logger = LogManager.getLogger(SudokuBoardTest.class.getName());
        logger.log(Level.INFO, "Logger ON");

        SudokuBoard board = new SudokuBoard();
        BoardException ex =  Assertions.assertThrows(
                BoardException.class,
                ()-> board.setBoardValueAt(0,0,boardSize+1));
        Assert.assertTrue(ex.getCause().getClass() == InvalidValueException.class);
        Assert.assertTrue(ex.getMessage().equals("Cannot set this value."));
        LogException(ex, logger);
    }
    @Test
    public void SetValueForInvalidIndex_ShouldThrowInvalidIndexException() {
        System.setProperty("log4j.configurationFile","./log4j2.xml");
        Logger logger = LogManager.getLogger(SudokuBoardTest.class.getName());
        logger.log(Level.INFO, "Logger ON");
        SudokuBoard board = new SudokuBoard();
        InvalidIndexException ex =  Assertions.assertThrows(
                InvalidIndexException.class,
                ()-> board.setBoardValueAt(boardSize+1,0,1));
        Assert.assertTrue(ex.getClass() == InvalidIndexException.class);
        Assert.assertTrue(ex.getMessage().equals("Cannot set value at given index: 10, 0"));
        LogException(ex, logger);
    }
}

