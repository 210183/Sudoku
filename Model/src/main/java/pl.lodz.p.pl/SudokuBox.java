package pl.lodz.p.pl;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static pl.lodz.p.pl.HelperMethods.createTwoDimensionalList;
import static pl.lodz.p.pl.SudokuConstants.boardSize;

public class SudokuBox implements Serializable {
    private static final int boxSize = 3;
    private List<FixedList> values = createTwoDimensionalList(boxSize, boxSize);

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuBox sudokuBox = (SudokuBox) o;
        return Objects.equals(values, sudokuBox.values);
    }

    @Override
    public int hashCode() {

        return Objects.hash(values);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("values", values)
                .toString();
    }
}
