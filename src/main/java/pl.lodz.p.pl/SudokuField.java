package pl.lodz.p.pl;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Objects;

import static pl.lodz.p.pl.SudokuConstants.*;

public class SudokuField  implements Serializable {

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
}
