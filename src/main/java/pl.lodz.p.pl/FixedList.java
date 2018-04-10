package pl.lodz.p.pl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class FixedList {

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

}