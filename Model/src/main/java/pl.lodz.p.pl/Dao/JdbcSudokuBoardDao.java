package pl.lodz.p.pl.Dao;

import pl.lodz.p.pl.Databse.DbManager;
import pl.lodz.p.pl.SudokuBoard;

import java.io.IOException;
import java.sql.SQLException;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable{

    private String boardName;

    public JdbcSudokuBoardDao(String boardName) {
        this.boardName = boardName;
    }

    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException {

        SudokuBoard newBoard = new SudokuBoard();
        //DbManager manager = new DbManager();
        try {
            newBoard = DbManager.getSudokuBoard(boardName);
        } catch (SQLException e) {
            //TODO:Exception
        }
        return newBoard;
    }

    @Override
    public void write(SudokuBoard obj) throws IOException {
        //DbManager manager = new DbManager();
        try {
            DbManager.createTables();
        } catch (SQLException e) {
            //TODO:Exception
        }
        try {
            DbManager.insertBoardWithFields(boardName, obj);
        } catch (SQLException e) {
            //TODO:Exception
        }
    }

    @Override
    public void close() throws IOException {

        DbManager.closeConnectionSource();

    }

    @Override
    public void finalize() {
        try {
            close();
        } catch (IOException e) {
            //TODO:Exception
        }
    }
}
