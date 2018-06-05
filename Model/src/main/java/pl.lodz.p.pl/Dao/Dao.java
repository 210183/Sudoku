package pl.lodz.p.pl.Dao;

import pl.lodz.p.pl.Exceptions.DataBaseException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Dao<T> {
    public T read() throws IOException, ClassNotFoundException, DataBaseException;
    public void write(T obj) throws IOException, DataBaseException;
}
