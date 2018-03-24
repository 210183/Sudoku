package pl.lodz.p.pl;

import java.security.InvalidParameterException;

import static pl.lodz.p.pl.SudokuConstants.*;

public class SudokuField {

    private int value;

    public SudokuField(int value) {
        if (isFieldValueInBounds(value)) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Invalid value");
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (isFieldValueInBounds(value)) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Invalid argument");
        }
    }
}
