package pl.lodz.p.pl;

import com.google.common.base.MoreObjects;
import pl.lodz.p.pl.Exceptions.BoardException;
import pl.lodz.p.pl.Exceptions.InvalidIndexException;
import pl.lodz.p.pl.Exceptions.InvalidValueException;

import javax.swing.*;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicLongArray;

import static pl.lodz.p.pl.SudokuConstants.*;
import static pl.lodz.p.pl.HelperMethods.*;

public class SudokuBoard implements Serializable, Cloneable {

    private List<FixedList> board = createTwoDimensionalList(boardSize, boardSize);

    public SudokuBoard() {
        // init SudokuField
        for (int i = 0; i < boardSize; i++) {

            for (int j = 0; j < boardSize; j++) {
                board.get(i).set(j, new SudokuField(0));
            }
        }
        List<SudokuField> rowValues = new ArrayList<>();
        for (int i = 1; i < boardSize + 1; i++) {
            rowValues.add(new SudokuField(i));
        }
        Collections.shuffle(rowValues);
        SudokuField[] row = new SudokuField[boardSize];
        for (int i = 0; i < boardSize; i++) {
            board.get(0).set(i, rowValues.get(i));
        }
    }

    public List<FixedList> getBoard() {
        List<FixedList> copy = createTwoDimensionalList(boardSize, boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                copy.get(i).set(j, board.get(i).get(j));
            }
        }
        return copy;
    }

    public void setBoardValueAt(int rowIndex, int columnIndex, int newValue) throws IllegalArgumentException {
        if(isIndexInBounds(rowIndex) && isIndexInBounds(columnIndex)) {
            try {
                board.get(rowIndex).get(columnIndex).setValue(newValue);
            }
            catch(InvalidValueException ex) {
                throw new BoardException("Cannot set this value.", ex);
            }
        }
        else {
            throw new InvalidIndexException("Cannot set value at given index: " + Integer.toString(rowIndex) + ", " + Integer.toString(columnIndex));
        }
    }

    public void consoleShow() {
        int rowCounter = 0;
        int colCounter = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (colCounter == 2) {
                    System.out.print(board.get(i).get(j).getValue() + " | ");
                    colCounter = 0;
                } else {
                    System.out.print(board.get(i).get(j).getValue() + " ");
                    colCounter++;
                }
            }
            if (rowCounter == 2) {
                System.out.print("\n");
                for (int k = 0; k < 12; k++) {
                    System.out.print("- ");
                }
                System.out.print("\n");
                rowCounter = 0;
            } else {
                System.out.print("\n");
                rowCounter++;
            }
        }
    }

    public boolean isValid(final BoardIndex boardIndex, int value) {
        int row = boardIndex.row;
        int col = boardIndex.col;
        if (getColumn(col).verify(value)) {
            if (getRow(row).verify(value)) {
                SudokuBox newBox = new SudokuBox();
                try {
                    newBox = getBox((row / 3) * 3, (col / 3) * 3);
                }catch(IllegalArgumentException e) {
                    throw new InvalidIndexException("Index is invalid", e);
                }
                if (newBox.verify(value)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static class BoardIndex {

        int row, col;

        public BoardIndex(int row, int col) {
            super();
            this.row = row;
            this.col = col;
        }

    }

    public BoardIndex getNextBoardIndex(final BoardIndex cur) {

        int row = cur.row;
        int col = cur.col;

        col++;
        if (col >= boardSize) {
            col = 0;
            row++;
        }

        if (row >= boardSize) {
            return null;
        }

        BoardIndex next = new BoardIndex(row, col);
        return next;
    }

    public SudokuBox getBox(int rowIndex, int colIndex) {
        if(rowIndex > SudokuConstants.boardSize - SudokuConstants.boxSize)
        {
            throw new InvalidIndexException("Cannot create box with that starting index");
        }
        if(colIndex > SudokuConstants.boardSize - SudokuConstants.boxSize)
        {
            throw new InvalidIndexException("Cannot create box with that starting index");
        }
        SudokuBox box = new SudokuBox();
        for (int i=0; i<SudokuConstants.boxSize; i++) {
            for (int j=0; j<SudokuConstants.boxSize; j++) {
                box.setValue(i, j, board.get(rowIndex + i).get(colIndex + j).getValue());
            }
        }
        return box;
    }
    public SudokuRow getRow(int rowIndex) {
        SudokuRow row = new SudokuRow();
        for (int i=0; i<SudokuConstants.boardSize; i++) {
            row.setValue(i, board.get(rowIndex).get(i).getValue());
        }
        return row;
    }
    public SudokuColumn getColumn(int colIndex) {
        SudokuColumn col = new SudokuColumn();
        for (int i=0; i<SudokuConstants.boardSize; i++) {
            col.setValue(i, board.get(i).get(colIndex).getValue());
        }
        return col;
    }

    public SudokuField getFieldAtIndexes(int row, int col){
        return board.get(row).get(col);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) o;
        return Objects.equals(board, that.board);
    }
    @Override
    public int hashCode() {

        return Objects.hash(board);
    }
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("board", board)
                .toString();
    }

    public SudokuBoard clone(){
        SudokuBoard newBoard = new SudokuBoard();
        for(int i=0; i<SudokuConstants.boardSize; i++) {
            for(int j=0; j<SudokuConstants.boardSize; j++) {
                try{
                    newBoard.setBoardValueAt(i,j,board.get(i).get(j).getValue());
                }catch(BoardException e){
                    throw new BoardException("Problem with board that was cloning too.", e);
                }
            }
        }
        return newBoard;
    }
}
