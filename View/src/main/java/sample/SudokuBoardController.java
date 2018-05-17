import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import pl.lodz.p.pl.BacktrackingSudokuSolver;
import pl.lodz.p.pl.SudokuBoard;
import pl.lodz.p.pl.SudokuField;

import java.net.URL;
import java.util.ResourceBundle;

public class SudokuBoardController implements Initializable {

    static Integer currentNumber = 1;

    @FXML
    SudokuBoard gameBoard = new SudokuBoard();

    EventHandler<ActionEvent> chooseNumberMethod  = SudokuBoardController::chooseNumber;
    EventHandler<ActionEvent> changeNumberMethod  = SudokuBoardController::changeNumber;

    @FXML
    GridPane NumbersPane;
    @FXML
    GridPane BoardPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Integer i = 0; i < 9; i++) {
            Integer buttonText = i+1;
            NumbersPane.add(createButton(buttonText.toString() , chooseNumberMethod), i, 0);
        }
    }


    private Button createButton(String text, EventHandler<ActionEvent> e) {
        Button button = new Button(text);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setOnAction(e);
        return button;
    }

    @FXML
    public static void chooseNumber(ActionEvent event){
        currentNumber = Integer.parseInt(((Button)event.getSource()).getText());
    }

    @FXML
    public static void changeNumber(ActionEvent event){
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
            }
        }
    }

    public void initData(SudokuBoard sudokuBoard) {
        gameBoard = sudokuBoard;
        showBoard();
    }

    public boolean isFieldZero(SudokuField field) {
        if(field.getValue() == 0)
            return true;
        else
            return false;
    }

}
