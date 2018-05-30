package pl.lodz.p.pl.Databse;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Fields")
public class Field {

    public Field() {

    }

    @DatabaseField(columnName = "Id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "Board_name", foreign = true, canBeNull = false)
    private Board board;

    @DatabaseField(columnName = "Index", canBeNull = false)
    private int index;

    @DatabaseField(columnName = "Value", canBeNull = false)
    private int value;

    @DatabaseField(columnName = "Blocked", canBeNull = false)
    private boolean changeable;

    public boolean isChangeable() {
        return changeable;
    }

    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(final Board board_id) {
        this.board = board_id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
