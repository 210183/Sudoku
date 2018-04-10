package pl.lodz.p.pl;

import java.util.Arrays;
import java.util.List;

import static pl.lodz.p.pl.SudokuConstants.isIndexInBounds;
import static pl.lodz.p.pl.SudokuConstants.boardSize;

public class SudokuLine {
    List<SudokuField> values = Arrays.asList(new SudokuField[boardSize]);
    //private SudokuField[] values = new SudokuField[boardSize];

    public SudokuLine() {
        for (int i = 0; i < boardSize; i++) {
            values.set(i, new SudokuField(0));
        }
    }

    public List<SudokuField> getValues() {
        List<SudokuField> copy = Arrays.asList(new SudokuField[boardSize]);
        for (int i = 0; i < boardSize; i++) {
            copy.set(i, values.get(i));
        }
        return copy;
    }

    public void setValue(int index, int value) {
        if (isIndexInBounds(index)) {
            values.get(index).setValue(value);
        }
    }

//    public boolean verify() {
//        boolean[] isNumberAlreadyFound = new boolean[boardSize + 1]; // from 0 - 9, cause zero means not filled yet
//        //region check row
//        Arrays.fill(isNumberAlreadyFound, false);
//        for (int cell = 0; cell < boardSize; cell++) {
//            if (values[cell].getValue() != 0) {
//                if (isNumberAlreadyFound[values[cell].getValue()]) {
//                    return false;
//                } else {
//                    isNumberAlreadyFound[values[cell].getValue()] = true;
//                }
//            }
//        }
//        return true;
//    }

    public boolean verify(int value) {
        for (int c = 0; c < 9; c++) {
            if (values.get(c).getValue() == value) {
                return false;
            }
        }
        return true;
    }
}
