package pl.lodz.p.pl.Databse;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import pl.lodz.p.pl.SudokuBoard;

import java.io.IOException;
import java.sql.SQLException;

public class DbManager {
    private static String databaseUrl = "jdbc:sqlite:Database";

    public static void setDbUrl(final String url) {
        databaseUrl = url;
    }

    public static String getDatabaseUrl() {
        return databaseUrl;
    }

    public static void createTables() throws SQLException {
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);

        TableUtils.createTableIfNotExists(connectionSource, Board.class);
        TableUtils.createTableIfNotExists(connectionSource, Field.class);

        try {
            connectionSource.close();
        } catch (IOException e) {
            e.getCause();
        }
    }

    public static void insertBoardWithFields(String boardName, SudokuBoard sudokuBoard) throws SQLException {
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        Dao<Board, String> boardDao = DaoManager.createDao(connectionSource, Board.class);

        Board board = new Board();
        board.setName(boardName);
        boardDao.createOrUpdate(board);

    }

    public static void deleteBoardAndFields(String boardName, ConnectionSource connectionSource) throws SQLException {
        Dao<Board, String> boardDao = DaoManager.createDao(connectionSource, Board.class);
        Board newBoard = new Board();
        newBoard.setName(boardName);
        boardDao.delete(newBoard);

        deleteFields(boardName, connectionSource);


    }

    public static void deleteFields(String boardName, ConnectionSource connectionSource) throws SQLException {
        Dao<Field, Integer> fieldDao = DaoManager.createDao(connectionSource, Field.class);
        if (fieldDao.query(fieldDao.queryBuilder().where().eq("Board_name", boardName).prepare()) != null) {
            DeleteBuilder<Field, Integer> deleteBuilder = fieldDao.deleteBuilder();
            deleteBuilder.where().eq("Board_name", boardName);
            deleteBuilder.delete();
        }
    }

}
