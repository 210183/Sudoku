package pl.lodz.p.pl;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FixedList implements Serializable{

    private ArrayList<SudokuField> list = new ArrayList<>();

    public FixedList(int size) {
        for (int i = 0; i < size; i++) {
            list.add(i, new SudokuField(0));
        }
    }

    public SudokuField get(int index) {
        return list.get(index);
    }

    public void set(int index, final SudokuField value) {
        list.set(index, value);
    }

    public int size() {
        return list.size();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FixedList fixedList = (FixedList) o;
        return Objects.equals(list, fixedList.list);
    }

    @Override
    public int hashCode() {

        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("list", list)
                .toString();
    }
}