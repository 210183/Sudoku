import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import pl.lodz.p.pl.BacktrackingSudokuSolver;
import pl.lodz.p.pl.SudokuBoard;
import pl.lodz.p.pl.SudokuConstants;
import pl.lodz.p.pl.SudokuField;
import borderPainter.BoxBordersPainter;

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

    ArrayList<Button> boardButtons  = new ArrayList<>();

    EventHandler<ActionEvent> chooseNumberMethod  = this::chooseNumber;
    EventHandler<ActionEvent> changeNumberMethod  = this::changeNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Integer i = 0; i < 9; i++) {
            Integer buttonText = i+1;
            Button button = createButton(buttonText.toString() , chooseNumberMethod);
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
    private void chooseNumber(ActionEvent event){
        Integer oldNumber = currentNumber;
        setDefaultStyle((Button)NumbersPane.getChildren().get(oldNumber-1));
        ((Button)event.getSource()).setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 2px;");
        currentNumber = Integer.parseInt(((Button)event.getSource()).getText());
    }

    @FXML
    private void changeNumber(ActionEvent event){
        ((Button)event.getSource()).setText(currentNumber.toString());
}

    private void showBoard(){
        for(int i =0; i < gameBoard.getBoard().size(); i++) {
            for(int j =0; j< gameBoard.getBoard().get(0).size(); j++) {
                SudokuField field = gameBoard.getBoard().get(i).get(j);
                String textForButton  = ((Integer)field.getValue()).toString();
                if(isFieldZero(field))
                    textForButton = "";
                Button b = createButton(textForButton, changeNumberMethod);
                b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton button = event.getButton();
                         if(button==MouseButton.SECONDARY){
                             ((Button)event.getSource()).setText("");
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

    public void initData(SudokuBoard sudokuBoard) {
        gameBoard = sudokuBoard;
        showBoard();
    }

    private boolean isFieldZero(SudokuField field) {
        if(field.getValue() == 0)
            return true;
        else
            return false;
    }

    private void setDefaultStyle(Button button)
    {
        button.setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 1px;");
    }



}
