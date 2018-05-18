package pl.lodz.p.pl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leveler {



    public void initializeBoardLevel(SudokuBoard board, DifficultyLevel level){

        List<Integer[]> availablePairs = new ArrayList<>();

        for(int i=0; i<SudokuConstants.boardSize; i++) {
            for(int j =0; j<SudokuConstants.boardSize;j++) {
                availablePairs.add(new Integer[]{i,j});
            }
        }
        Collections.shuffle(availablePairs);
        for (int i =0; i<getEmptyBoxesNumber(level); i++) {
            board.setBoardValueAt(availablePairs.get(i)[0],availablePairs.get(i)[1],0);
        }
    }

    public Integer getEmptyBoxesNumber(DifficultyLevel level) {

        if(level == DifficultyLevel.Easy)
            return 35;
        if(level == DifficultyLevel.Medium)
            return 43;
        if(level == DifficultyLevel.Hard)
            return 50;
        return null;
    }

}
