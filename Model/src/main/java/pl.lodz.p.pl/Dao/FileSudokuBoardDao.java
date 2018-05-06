package pl.lodz.p.pl.Dao;

import pl.lodz.p.pl.SudokuBoard;

import java.io.*;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    public String fileName;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;

    public FileSudokuBoardDao(final String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException {
        fileInputStream = new FileInputStream(fileName);
        objectInputStream = new ObjectInputStream(fileInputStream);
        SudokuBoard board = (SudokuBoard) objectInputStream.readObject();
        objectInputStream.close();
        return board;
    }

    @Override
    public void write(final SudokuBoard sudoku) throws IOException {
        fileOutputStream = new FileOutputStream(fileName);
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(sudoku);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    @Override
    public void finalize() throws Exception {
        close();
    }

    @Override
    public void close() throws Exception {
        if (fileInputStream != null) {
            fileInputStream.close();
        }
        if (objectInputStream != null) {
            objectInputStream.close();
        }
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
        if (objectOutputStream != null) {
            objectOutputStream.close();
        }
    }
}
