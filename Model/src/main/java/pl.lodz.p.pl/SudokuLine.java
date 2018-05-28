package pl.lodz.p.pl;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static pl.lodz.p.pl.SudokuConstants.isIndexInBounds;
import static pl.lodz.p.pl.SudokuConstants.boardSize;

public class SudokuLine  implements Serializable {
    List<SudokuField> values = Arrays.asList(new SudokuField[boardSize]);

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

    public boolean verify(int value) {
        for (int c = 0; c < 9; c++) {
            if (values.get(c).getValue() == value) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuLine that = (SudokuLine) o;
        return Objects.equals(values, that.values);
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
