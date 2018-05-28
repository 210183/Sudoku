package pl.lodz.p.pl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelperMethods {

    public static List<FixedList> createTwoDimensionalList(int rows, int columns) {
        FixedList[] row = new FixedList[rows];
        List<FixedList> board = Arrays.asList(row);
        for (int i = 0; i < board.size(); i++) {
            FixedList temp = new FixedList(columns);
            board.set(i, temp);
        }
        return board;
    }
    public static boolean Equals(final List<SudokuField> first, final List<SudokuField> second) {
        if (first.size() != second.size()) {
            return false;
        }
        for (int i=0; i < first.size(); i++) {
            if (first.get((i)).getValue() != second.get(i).getValue()) {
                return false;
            }
        }
        return true;
    }
    public static void LogException(Exception ex, Logger logger) {
        logger.log(Level.ERROR, ex.getClass().toString() +"\r\n"+ " Message = "  + ex.getMessage());
        Throwable throwable = ex.getCause();
        while(throwable != null)
        {
            logger.log(Level.ERROR, "   Caused: " + throwable.getClass().toString() +"\r\n"+" Message = "  + throwable.getMessage());
            throwable = throwable.getCause();
        }
    }
}
