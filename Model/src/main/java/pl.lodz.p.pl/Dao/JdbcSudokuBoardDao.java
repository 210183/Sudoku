package pl.lodz.p.pl.Dao;

import pl.lodz.p.pl.Databse.DbManager;
import pl.lodz.p.pl.Exceptions.DataBaseException;
import pl.lodz.p.pl.SudokuBoard;

import java.io.IOException;
import java.sql.SQLException;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable{

    private String boardName;

    public JdbcSudokuBoardDao(String boardName) {
        this.boardName = boardName;
    }

    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException, DataBaseException {

        SudokuBoard newBoard = new SudokuBoard();
        try {
            newBoard = DbManager.getSudokuBoard(boardName);
        } catch (SQLException e) {
            throw new DataBaseException("Error in reading from database");
        }
        return newBoard;
    }

    @Override
    public void write(SudokuBoard obj) throws IOException, DataBaseException {
        try {
            DbManager.createTables();
        } catch (SQLException e) {
            throw new DataBaseException("Can't create tables");
        }
        try {
            DbManager.insertBoardWithFields(boardName, obj);
        } catch (SQLException e) {
            throw new DataBaseException("Can't add sudoku board to database");
        }
    }

    @Override
    public void close() throws IOException {

        DbManager.closeConnectionSource();

    }

    @Override
    public void finalize() throws DataBaseException{
        try {
            close();
        } catch (IOException e) {
            throw new DataBaseException("Can't close connection");
        }
    }
}
