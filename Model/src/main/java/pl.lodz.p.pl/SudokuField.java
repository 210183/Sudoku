package pl.lodz.p.pl;

import com.google.common.base.MoreObjects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.lodz.p.pl.Exceptions.InvalidValueException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Objects;

import static pl.lodz.p.pl.SudokuConstants.*;

public class SudokuField  implements Serializable, Comparable<SudokuField> {


    private boolean blocked  = true;
    private int intValue;
    transient private IntegerProperty value = new SimpleIntegerProperty();

    public SudokuField(int value) {
        setValue(value);
        intValue = value;
    }

    public IntegerProperty getProperty() {
        return this.value;
    }

    public int getValue() {
        return value.getValue();
    }

    public void setValue(int value) {
        if (isFieldValueInBounds(value)) {
            this.value.setValue(value);
            intValue = value;
        } else {
            throw new InvalidValueException("Invalid value: " + Integer.toString(value));
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
        return Integer.compare(this.value.getValue(), o.getValue());
    }

    //customized serialization
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(value.get());
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int x = in.readInt();
        value = new SimpleIntegerProperty(x);
    }
}
