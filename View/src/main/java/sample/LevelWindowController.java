import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.lodz.p.pl.BacktrackingSudokuSolver;
import pl.lodz.p.pl.DifficultyLevel;
import pl.lodz.p.pl.Exceptions.BacktrackingSolverException;
import pl.lodz.p.pl.Exceptions.InvalidIndexException;
import pl.lodz.p.pl.Exceptions.InvalidValueException;
import pl.lodz.p.pl.Leveler;
import pl.lodz.p.pl.SudokuBoard;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;



public class LevelWindowController implements Initializable {

//    private final static Logger LOGGER = Logger.getLogger(LevelWindowController.class.getName());
//    FileHandler fh;
//
    Logger logger;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.setProperty("log4j.configurationFile","./log4j2.xml");
        Logger logger = LogManager.getLogger(LevelWindowController.class.getName());
        logger.log(Level.INFO, "Logger ON");
    }


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
        try {
            solver.solve(fullBoard);
        } catch (BacktrackingSolverException e) {
            //LOGGER.info(e.getStackTrace().toString());
            logger.log(Level.ERROR, e.getStackTrace().toString());
        }
        try {
            gameBoard = fullBoard.clone();
        }catch(InvalidValueException e){
            logger.log(Level.ERROR, e.getStackTrace().toString());
        }
        try {
            new Leveler().initializeBoardLevel(gameBoard, level);
        }catch(InvalidIndexException e){
            logger.log(Level.ERROR, e.getStackTrace().toString());
        }

        SudokuBoardController boardController = loader.getController();
        boardController.initData(gameBoard, logger);

        primaryStage.setTitle("Board Window");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

