package pl.lodz.p.pl;

import java.util.Arrays;
import java.util.List;

import static pl.lodz.p.pl.HelperMethods.createTwoDimensionalList;
import static pl.lodz.p.pl.SudokuConstants.boardSize;

public class SudokuBox {
    private static final int boxSize = 3;
    private List<FixedList> values = createTwoDimensionalList(boxSize, boxSize);
    //private SudokuField[][] values = new SudokuField[boxSize][boxSize];

    public SudokuBox() {
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                values.get(i).set(j, new SudokuField(0));
            }
        }
    }

    public void setValue(int rowIndex, int columnIndex, int value) {
        values.get(rowIndex).get(columnIndex).setValue(value);
    }

//    public boolean verify() {
//        boolean[] isNumberAlreadyFound = new boolean[boardSize + 1]; // from 0 - 9, cause zero means not filled yet
//        //region check row
//        Arrays.fill(isNumberAlreadyFound, false);
//        for (int row = 0; row < boxSize; row++) {
//            for (int col = 0; col < boxSize; col++) {
//                if (values[row][col].getValue() != 0) {
//                    if (isNumberAlreadyFound[values[row][col].getValue()]) {
//                        return false;
//                    } else {
//                        isNumberAlreadyFound[values[row][col].getValue()] = true;
//                    }
//                }
//            }
//        }
//        return true;
//    }

    public boolean verify(int value) {
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                if (values.get(i).get(j).getValue() == value) {
                    return false;
                }
            }
        }
        return true; // if reached here, value is ok
    }
}
