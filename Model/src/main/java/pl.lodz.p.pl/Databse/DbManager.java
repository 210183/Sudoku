package pl.lodz.p.pl.Databse;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import pl.lodz.p.pl.SudokuBoard;
import pl.lodz.p.pl.SudokuConstants;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private static String databaseUrl = "jdbc:sqlite:Database";

    public static void setDbUrl(final String url) {
        databaseUrl = url;
    }

    public static String getDatabaseUrl() {
        return databaseUrl;
    }

    static ConnectionSource connectionSource;

    public static  void closeConnectionSource() throws IOException {
        connectionSource.close();
    }


    public static void createTables() throws SQLException {
         connectionSource = new JdbcConnectionSource(databaseUrl);

        TableUtils.createTableIfNotExists(connectionSource, Board.class);
        TableUtils.createTableIfNotExists(connectionSource, Field.class);

        try {
            connectionSource.close();
        } catch (IOException e) {
            e.getCause();
        }
    }

    public static void insertBoardWithFields(String boardName, SudokuBoard sudokuBoard) throws SQLException, IOException {
         connectionSource = new JdbcConnectionSource(databaseUrl);
        Dao<Board, String> boardDao = DaoManager.createDao(connectionSource, Board.class);

        deleteFields(boardName);
        Board board = new Board();
        board.setName(boardName);
        boardDao.createOrUpdate(board);
        insertFields(boardName, board, sudokuBoard);
        connectionSource.close();

    }

    public static void deleteBoardAndFields(String boardName, ConnectionSource connectionSource) throws SQLException {
        Dao<Board, String> boardDao = DaoManager.createDao(connectionSource, Board.class);
        Board newBoard = new Board();
        newBoard.setName(boardName);
        boardDao.delete(newBoard);

        deleteFields(boardName);

    }

    public static void deleteFields(String boardName) throws SQLException {
        Dao<Field, Integer> fieldDao = DaoManager.createDao(connectionSource, Field.class);
        if (fieldDao.query(fieldDao.queryBuilder().where().eq("Board_name", boardName).prepare()) != null) {
            DeleteBuilder<Field, Integer> deleteBuilder = fieldDao.deleteBuilder();
            deleteBuilder.where().eq("Board_name", boardName);
            deleteBuilder.delete();
        }
    }

    public static void insertFields(String boardName, Board board, SudokuBoard sudokuBoard) throws SQLException {
        Dao<Field, Integer> fieldDao = DaoManager.createDao(connectionSource, Field.class);
        for(int i = 0; i<SudokuConstants.boardSize; i++){
            for(int j=0; j<SudokuConstants.boardSize; j++){
                Field field = new Field();
                field.setBoard(board);
                field.setIndex(i*SudokuConstants.boardSize + j);
                field.setValue(sudokuBoard.getFieldAtIndexes(i,j).getValue());
                field.setBlocked(sudokuBoard.getFieldAtIndexes(i,j).IsBlocked());
                fieldDao.createOrUpdate(field);
            }
        }
    }

    public static SudokuBoard getSudokuBoard(String sudokuName) throws SQLException, IOException {
            ConnectionSource connectionSource = null;
            Dao<Field, Integer> fieldDao = null;
            List<Field> result = null;
            connectionSource = new JdbcConnectionSource(databaseUrl);
            fieldDao = DaoManager.createDao(connectionSource, Field.class);
            result = fieldDao.query(fieldDao.queryBuilder().
                     where().
                     eq("Board_name", sudokuName).
                     prepare());
            SudokuBoard dbBoard = new SudokuBoard();

            for(int i=0; i<SudokuConstants.boardSize; i++){
                for(int j =0; j< SudokuConstants.boardSize; j++){
                    dbBoard.getFieldAtIndexes(i,j).setValue(result.get(i*SudokuConstants.boardSize + j).getValue());
                    dbBoard.getFieldAtIndexes(i,j).setIsBlocked(result.get(i*SudokuConstants.boardSize + j).isBlocked());
                }

            }
            connectionSource.close();
            return dbBoard;
    }

    public static List<String> selectAllBoards() throws SQLException, IOException {
        List<Board> tmp = null;
        ConnectionSource connectionSource = null;
        connectionSource = new JdbcConnectionSource(databaseUrl);
        Dao<Board, String> boardDao = DaoManager.createDao(connectionSource, Board.class);
        tmp = boardDao.query(boardDao.queryBuilder().prepare());
        connectionSource.close();

        List<String> tmpString = new ArrayList<String>();

        for(int i=0; i< tmp.size(); i++) {
            tmpString.add(tmp.get(i).getName());
        }

        return tmpString;
    }

}
