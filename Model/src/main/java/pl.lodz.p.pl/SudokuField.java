package pl.lodz.p.pl;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Objects;

import static pl.lodz.p.pl.SudokuConstants.*;

public class SudokuField  implements Serializable, Comparable<SudokuField> {

    private int value;
    private boolean blocked  = true;

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

    public void setIsBlocked(boolean b) {
        blocked = b;
    }

    public boolean IsBlocked() {
        return blocked;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .toString();
    }

    @Override
    public int compareTo(SudokuField o) {
        return Integer.compare(this.value, o.getValue());
    }

}
