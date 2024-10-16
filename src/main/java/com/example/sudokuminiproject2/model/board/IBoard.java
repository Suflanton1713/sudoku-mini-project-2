package com.example.sudokuminiproject2.model.board;

import java.util.ArrayList;
import java.util.List;

public interface IBoard {


    List<List<Integer>> board = new ArrayList<>(6);

    boolean isNumberByColumnAllowed(int numberSelected, int column, int row);

    boolean isNumberByRowAllowed(int numberSelected, int column, int row);

    boolean isNumberByBoxAllowed(int numberSelected, int column, int row);

    void setNumberByIndex(int number, int column, int row);

    int getNumberByIndex(int column, int row);

    String showBoard();

    default String showIdealGame(){
        String finalMessage = "";
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                finalMessage = finalMessage + board.get(j).get(i) + " ";
            }
            finalMessage = finalMessage + "\n";
        }
        return finalMessage;
    }

}
