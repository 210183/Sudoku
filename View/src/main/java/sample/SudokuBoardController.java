import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import borderPainter.BoxBordersPainter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.NumberStringConverter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import pl.lodz.p.pl.BoardValidator;
import pl.lodz.p.pl.Dao.FileSudokuBoardDao;
import pl.lodz.p.pl.Dao.JdbcSudokuBoardDao;
import pl.lodz.p.pl.Dao.SudokuBoardDaoFactory;
import pl.lodz.p.pl.Databse.Board;
import pl.lodz.p.pl.Databse.DbManager;
import pl.lodz.p.pl.Exceptions.DataBaseException;
import pl.lodz.p.pl.SudokuBoard;
import pl.lodz.p.pl.SudokuConstants;
import pl.lodz.p.pl.SudokuField;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.ConstantCallSite;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static javafx.beans.binding.Bindings.bindBidirectional;
import static pl.lodz.p.pl.HelperMethods.LogException;


public class SudokuBoardController implements Initializable {

    static Integer currentNumber = 1;
    Logger logger;

    @FXML
    SudokuBoard gameBoard = new SudokuBoard();
    @FXML
    GridPane NumbersPane;
    @FXML
    GridPane BoardPane;

    ArrayList<Button> boardButtons = new ArrayList<>();

    EventHandler<ActionEvent> chooseNumberMethod = this::chooseNumber;
    EventHandler<ActionEvent> changeNumberMethod = this::changeNumber;

    public void initData(SudokuBoard playBoard, Logger logger) {
        gameBoard = playBoard;
        this.logger = logger;
        showBoard();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Integer i = 0; i < 9; i++) {
            Integer buttonText = i + 1;
            Button button = createButton(buttonText.toString(), chooseNumberMethod);
            NumbersPane.add(button, i, 0);
        }
    }

    private Button createButton(String text, EventHandler<ActionEvent> e) {
        Button button = new Button(text);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setOnAction(e);
        setDefaultStyle(button);
        return button;
    }

    @FXML
    private void chooseNumber(ActionEvent event) {
        Integer oldNumber = currentNumber;
        setDefaultStyle((Button) NumbersPane.getChildren().get(oldNumber - 1));
        ((Button) event.getSource()).setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 2px;");
        currentNumber = Integer.parseInt(((Button) event.getSource()).getText());
    }

    @FXML
    private void changeNumber(ActionEvent event) {
        Button b = (Button) event.getSource();
        b.setText(currentNumber.toString());
    }

    private void showBoard() {
        for (int i = 0; i < SudokuConstants.boardSize; i++) {
            for (int j = 0; j < SudokuConstants.boardSize; j++) {
                SudokuField field = gameBoard.getBoard().get(i).get(j);

                Button b = createButton(null, changeNumberMethod);
                b.setDisable(field.IsBlocked());
                b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton button = event.getButton();
                        if (button == MouseButton.SECONDARY) {
                            ((Button) event.getSource()).setText("");
                        }
                    }
                });
                bindBidirectional(b.textProperty(), field.getProperty(), new NumberStringConverter());
                if(Integer.parseInt(b.getText()) == 0)
                    b.setText("");
                BoardPane.add(b, j, i);
                boardButtons.add(b);
            }
        }
        BoxBordersPainter painter = new BoxBordersPainter();
        painter.drawBoxBorders(boardButtons);
    }



    private boolean isFieldZero(SudokuField field) {
        if (field.getValue() == 0)
            return true;
        else
            return false;
    }

    private void setDefaultStyle(Button button) {
        button.setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 1px;");
    }

    private Button getButton(int row, int col) {
        return boardButtons.get(row*SudokuConstants.boardSize + col);
    }
    @FXML
    private boolean validateGameBoard() {
        BoardValidator Bv = new BoardValidator();
        boolean result = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Results");
        alert.setHeaderText(null);

        result = Bv.validate(gameBoard);
        if(result) {
            alert.setContentText("You win!!!");
        }
        else{
            alert.setContentText("There are still some incorrect fields!");
        }
        alert.showAndWait();
        return result;
    }


    private String chooseFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to read/save");
        String file = fileChooser.showOpenDialog(new Stage()).getPath();
        return file;
    }

    @FXML
    private void saveGame() {
        String filePath = chooseFile();
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        FileSudokuBoardDao newDao = factory.getFileDao(filePath);
        //newDao.write(prepareBoardToSave(gameBoard));
        try {
            newDao.write(gameBoard);
        } catch (IOException e) {
            LogException(e, logger);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Saved");
        alert.setHeaderText(null);
        alert.setContentText("Saved!");
        alert.showAndWait();

    }
    @FXML
    private void openGame(){
        String filePath = chooseFile();
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        FileSudokuBoardDao newDao = factory.getFileDao(filePath);
        try {
            gameBoard = newDao.read();
        } catch (IOException e) {
            LogException(e, logger);
        } catch (ClassNotFoundException e) {
            LogException(e, logger);
        }
        BoardPane.getChildren().clear();
        showBoard();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Open");
        alert.setHeaderText(null);
        alert.setContentText("Done!");
        alert.showAndWait();
    }

    @FXML
    private void saveToDb() {
        TextInputDialog dialog = new TextInputDialog("save");
        dialog.setTitle("Database save");
        dialog.setHeaderText("Save to database");
        dialog.setContentText("Enter name of the save:");

        Optional<String> result = dialog.showAndWait();
        String boardName = "Default";
        if(result.isPresent()){
            boardName = result.get();
        }
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        JdbcSudokuBoardDao dbDao = factory.getDbDao(boardName);

        try {
            dbDao.write(gameBoard);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Saved");
        alert.setHeaderText(null);
        alert.setContentText("Saved!");
        alert.showAndWait();
    }

    @FXML
    private void openFromDb() {
        DbManager manager = new DbManager();
        List<String> boardsList = new ArrayList<>();
        try {
             boardsList = manager.selectAllBoards();
        } catch (SQLException e) {
            LogException(e, logger);
        } catch (IOException e) {
            LogException(e, logger);
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(boardsList.get(0), boardsList);
        dialog.setTitle("Database open");
        dialog.setHeaderText("Open from database");
        dialog.setContentText("Choose sudoku board:");

        Optional<String> result = dialog.showAndWait();
        String boardName = "";
        if (result.isPresent()) {
            boardName = result.get();
        }

        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        JdbcSudokuBoardDao dbDao = factory.getDbDao(boardName);

        try {
            gameBoard = dbDao.read();
            BoardPane.getChildren().clear();
            showBoard();
        } catch (IOException e) {
            LogException(e, logger);
        } catch (ClassNotFoundException e) {
            LogException(e, logger);
        } catch (DataBaseException e) {
            LogException(e, logger);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Open");
        alert.setHeaderText(null);
        alert.setContentText("Done!");
        alert.showAndWait();

    }
}



