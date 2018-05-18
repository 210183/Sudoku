package borderPainter;

import javafx.scene.control.Button;
import pl.lodz.p.pl.SudokuConstants;

import java.util.ArrayList;

public class BoxBordersPainter {

    public void drawBoxBorders(ArrayList<Button> boardButtons) {
        for (int i =0; i<boardButtons.size(); i++ )
        {
            drawOnlyLeftBorderIfShould(getRowIndex(i), getColumnIndex(i), boardButtons.get(i));
            drawOnlyRightBorderIfShould(getRowIndex(i), getColumnIndex(i), boardButtons.get(i));
            drawOnlyTopBorderIfShould(getRowIndex(i), getColumnIndex(i), boardButtons.get(i));
            drawOnlyBottomBorderIfShould(getRowIndex(i), getColumnIndex(i), boardButtons.get(i));
            drawOnlyTopLeftBorderIfShould(getRowIndex(i), getColumnIndex(i), boardButtons.get(i));
            drawOnlyTopRightBorderIfShould(getRowIndex(i), getColumnIndex(i), boardButtons.get(i));
            drawOnlyBottomRightBorderIfShould(getRowIndex(i), getColumnIndex(i), boardButtons.get(i));
            drawOnlyBottomLeftBorderIfShould(getRowIndex(i), getColumnIndex(i), boardButtons.get(i));
        }
    }

    private void drawOnlyLeftBorderIfShould(Integer rowIndex, Integer colIndex, Button button) {
        if( ((rowIndex-1) % 3 == 0) && (colIndex % 3 == 0) )
        {
            button.setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 1px 1 1 3 ;");
        }
    }
    private void drawOnlyRightBorderIfShould(Integer rowIndex, Integer colIndex, Button button) {
        if( ((rowIndex-1) % 3 == 0) && ((colIndex-2) % 3 == 0) )
        {
            button.setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 1px 3 1 1 ;");
        }
    }
    private void drawOnlyTopBorderIfShould(Integer rowIndex, Integer colIndex, Button button) {
        if( ((rowIndex) % 3 == 0) && ((colIndex-1) % 3 == 0) )
        {
            button.setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 3px 1 1 1 ;");
        }
    }
    private void drawOnlyBottomBorderIfShould(Integer rowIndex, Integer colIndex, Button button) {
        if( ((rowIndex-2) % 3 == 0) && ((colIndex-1) % 3 == 0) )
        {
            button.setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 1px 1 3 1 ;");
        }
    }
    private void drawOnlyTopLeftBorderIfShould(Integer rowIndex, Integer colIndex, Button button) {
        if( ((rowIndex) % 3 == 0) && ((colIndex) % 3 == 0) )
        {
            button.setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 3px 1 1 3 ;");
        }
    }
    private void drawOnlyTopRightBorderIfShould(Integer rowIndex, Integer colIndex, Button button) {
        if( ((rowIndex) % 3 == 0) && ((colIndex-2) % 3 == 0) )
        {
            button.setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 3px 3 1 1 ;");
        }
    }
    private void drawOnlyBottomRightBorderIfShould(Integer rowIndex, Integer colIndex, Button button) {
        if( ((rowIndex-2) % 3 == 0) && ((colIndex-2) % 3 == 0) )
        {
            button.setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 1px 3 3 1 ;");
        }
    }
    private void drawOnlyBottomLeftBorderIfShould(Integer rowIndex, Integer colIndex, Button button) {
        if( ((rowIndex-2) % 3 == 0) && ((colIndex) % 3 == 0) )
        {
            button.setStyle("-fx-border-color: #2b0c5a; -fx-border-width: 1px 1 3 3 ;");
        }
    }


    private Integer getRowIndex(final Integer oneDIndex) {
        int rowIndex = oneDIndex / SudokuConstants.boardSize;
        return rowIndex;
    }
    private Integer getColumnIndex(final Integer oneDIndex) {
        int colIndex = oneDIndex % SudokuConstants.boardSize;
        return colIndex;
    }
}
