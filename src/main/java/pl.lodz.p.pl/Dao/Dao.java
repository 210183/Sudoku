package pl.lodz.p.pl.Dao;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Dao<T> {
    public T read() throws IOException, ClassNotFoundException;
    public void write(T obj) throws IOException;
}
