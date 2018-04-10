package pl.lodz.p.pl;

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

}
