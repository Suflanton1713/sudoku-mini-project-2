package com.example.sudokuminiproject2.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard extends BoardAdapter{

    private List<List<Integer>> gameBoard = new ArrayList<>(6);

    public GameBoard(){
        for(int i=0; i<6; i++){
            List<Integer> row = new ArrayList<>(6);
            for (int x = 0; x < 6; x++) {
                row.add(0);
            }

            gameBoard.add(row);
        }
    }

    public void setNumberByIndex(int number, int column, int row){
        gameBoard.get(column).set(row, number);
    }

    public void setInitialHints(Board defaultBoard){
        System.out.println("Setting initial hints");
        Random random = new Random();
        int randomRow, randomCol, number;
        for(int i = 0; i<4; i+=3){
            for(int j = 0; j<6; j++){
                do{
                    randomRow = (j / 2);
                    randomRow = randomRow *2 +(random.nextInt(2));
                    randomCol = (i / 3);
                    randomCol = randomCol * 3 + (random.nextInt(3));
                    System.out.println("On row " + randomRow + " on Column " + randomCol);
                    System.out.println("The number there is " + getNumberByIndex(randomCol, randomRow));
                }while(getNumberByIndex(randomCol, randomRow) != 0);
                System.out.println("Introducing new number " + defaultBoard.getNumberByIndex(randomCol, randomRow));

                number = defaultBoard.getNumberByIndex(randomCol, randomRow);
                setNumberByIndex(number ,randomCol, randomRow);
            }
        }

    }

    public int getNumberByIndex(int column, int row){  return gameBoard.get(column).get(row);  };

    public String showBoard(){
        String finalMessage = "";
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                finalMessage = finalMessage + gameBoard.get(j).get(i) + "  ";
            }
            finalMessage = finalMessage + "\n";
        }
        return finalMessage;

    }

}
