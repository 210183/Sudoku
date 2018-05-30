package pl.lodz.p.pl.Databse;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Boards")
public class Board {

    public Board() {
    }

    @DatabaseField(id = true, columnName = "Name", unique = true)
    private String name;

    @ForeignCollectionField()
    private ForeignCollection<Field> fields;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ForeignCollection<Field> getFields() {
        return fields;
    }
}
