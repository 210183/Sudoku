import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import pl.lodz.p.pl.BoardValidator;
import pl.lodz.p.pl.Dao.FileSudokuBoardDao;
import pl.lodz.p.pl.Dao.SudokuBoardDaoFactory;
import pl.lodz.p.pl.SudokuBoard;
import pl.lodz.p.pl.SudokuConstants;
import pl.lodz.p.pl.SudokuField;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

public class SudokuBoardController implements Initializable {

    static Integer currentNumber = 1;

    @FXML
    SudokuBoard gameBoard = new SudokuBoard();
    @FXML
    GridPane NumbersPane;
    @FXML
    GridPane BoardPane;

    ArrayList<Button> boardButtons = new ArrayList<>();

    EventHandler<ActionEvent> chooseNumberMethod = this::chooseNumber;
    EventHandler<ActionEvent> changeNumberMethod = this::changeNumber;

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
        for (int i = 0; i < gameBoard.getBoard().size(); i++) {
            for (int j = 0; j < gameBoard.getBoard().get(0).size(); j++) {
                SudokuField field = gameBoard.getBoard().get(i).get(j);
                String textForButton = ((Integer) field.getValue()).toString();
                if (isFieldZero(field))
                    textForButton = "";
                Button b = createButton(textForButton, changeNumberMethod);
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
                BoardPane.add(b, j, i);
                boardButtons.add(b);
            }
        }
        BoxBordersPainter painter = new BoxBordersPainter();
        painter.drawBoxBorders(boardButtons);
    }

    public void initData(SudokuBoard playBoard) {
        gameBoard = playBoard;
        showBoard();
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
    private boolean validateGameBoard(){
        BoardValidator Bv = new BoardValidator();
        boolean result = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Results");
        alert.setHeaderText(null);

        //Saving buttons state
        for(int i =0; i < SudokuConstants.boardSize; i++) {
            for(int j =0; j < SudokuConstants.boardSize; j++){
                if(!gameBoard.getFieldAtIndexes(i, j).IsBlocked()){
                    String textBox = getButton(i,j).getText();
                    if(textBox == ""){
                        result = false;
                        alert.setContentText("There are still some empty fields!");
                        alert.showAndWait();
                        return false;
                    }
                    Integer value = Integer.parseInt(textBox);
                    gameBoard.setBoardValueAt(i,j, value);
                }
            }
        }
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
    private void saveGame() throws IOException {
        String filePath = chooseFile();
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        FileSudokuBoardDao newDao = factory.getFileDao(filePath);
        newDao.write(prepareBoardToSave(gameBoard));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Saved");
        alert.setHeaderText(null);
        alert.setContentText("Saved!");
        alert.showAndWait();
    }
    @FXML
    private void openGame() throws IOException, ClassNotFoundException {
        String filePath = chooseFile();
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        FileSudokuBoardDao newDao = factory.getFileDao(filePath);
        gameBoard = newDao.read();
        
        for (int i = 0; i < SudokuConstants.boardSize; i++) {
            for (int j = 0; j < SudokuConstants.boardSize; j++) {
                SudokuField field= gameBoard.getFieldAtIndexes(i, j);
                String textForButton = ((Integer) field.getValue()).toString();
                Button b = getButton(i,j);
                b.setDisable(field.IsBlocked());
                if(isFieldZero(field))
                    textForButton = "";
                b.setText(textForButton);
                }
            }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Open");
        alert.setHeaderText(null);
        alert.setContentText("Done!");
        alert.showAndWait();
    }


    private SudokuBoard prepareBoardToSave(SudokuBoard board) {
        Integer value =0;
        for (int i = 0; i < SudokuConstants.boardSize; i++) {
            for (int j = 0; j < SudokuConstants.boardSize; j++) {
                if (!board.getFieldAtIndexes(i, j).IsBlocked()) {
                    String textBox = getButton(i, j).getText();
                    if (textBox == "") {
                        value = 0;
                    } else {
                        value = Integer.parseInt(textBox);
                    }
                    board.setBoardValueAt(i, j, value);
                }
            }
        }
        return board;
    }
}
