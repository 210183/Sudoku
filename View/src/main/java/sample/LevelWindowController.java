import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pl.lodz.p.pl.BacktrackingSudokuSolver;
import pl.lodz.p.pl.DifficultyLevel;
import pl.lodz.p.pl.Leveler;
import pl.lodz.p.pl.SudokuBoard;

import java.io.IOException;
import java.net.URL;

public class LevelWindowController {
    @FXML
    private Button easyButton;
    @FXML
    private Button mediumButton;
    @FXML
    private Button hardButton;

    SudokuBoard fullBoard = new SudokuBoard();
    SudokuBoard gameBoard = new SudokuBoard();

    @FXML
    public void easyButton_Click(ActionEvent event) throws IOException {
        createWindow(DifficultyLevel.Easy);
    }
    @FXML
    public void mediumButton_Click(ActionEvent event) throws IOException {
        createWindow(DifficultyLevel.Medium);
    }
    @FXML
    public void hardButton_Click(ActionEvent event) throws IOException {
        createWindow(DifficultyLevel.Hard);
    }

    public void createWindow(DifficultyLevel level ) throws IOException {


        URL url = getClass().getResource("/SudokuBoardWindow.fxml");

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(url);

        Parent root = loader.load();


        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        solver.solve(fullBoard);
        gameBoard = fullBoard.clone();
        new Leveler().initializeBoardLevel(gameBoard, level);

        SudokuBoardController boardController = loader.getController();
        boardController.initData(gameBoard);

        primaryStage.setTitle("Board Window");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}

