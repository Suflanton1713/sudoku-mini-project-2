package com.example.sudokuminiproject2.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard extends BoardAdapter{

    private List<List<Integer>> gameBoard = new ArrayList<>(6);
    private Board idealBoard;
    private int hints;
    private boolean isGameOver;

    public GameBoard(Board idealBoard){
        hints = 5;
        isGameOver = false;
        this.idealBoard = idealBoard;
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

    public void setInitialHints(){
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
                System.out.println("Introducing new number " + idealBoard.getNumberByIndex(randomCol, randomRow));

                number = idealBoard.getNumberByIndex(randomCol, randomRow);
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

    public class Hint{

        public int getHints(){return hints;}

        public boolean canUseHints(){return hints !=0 && isGameOver;}

        public int[] randomHint() {
            Random rand = new Random();
            int randomRowPosition = -1;
            int randomColPosition = -1;
            randomRowPosition = rand.nextInt(6);
            randomColPosition = rand.nextInt(6);
            boolean isAnAvailablePosition = false;
            do {

                System.out.println("The hint col is " + randomColPosition);
                System.out.println("The hint row is " + randomRowPosition);
                if(getNumberByIndex(randomColPosition, randomRowPosition) == idealBoard.getNumberByIndex(randomColPosition, randomRowPosition) ){

                    //The following code is an algorithm that allows to search into cols and rows for a space with hints available
                    //It goes by columns until it reaches the sixth column, then he jumps off the actual row towards the next row and starts again in the first column
                    //Makes the same thing with the rows.
                    if(randomColPosition==5){
                        randomColPosition= 0;
                        if(randomRowPosition==5){
                            randomRowPosition = 0;
                        }else{
                            randomRowPosition++;
                            System.out.println("We moved the row " + randomRowPosition);
                        }
                    }else{
                        randomColPosition++;
                        System.out.println("We moved the col " + randomColPosition);
                    }


                }else{
                    isAnAvailablePosition = true;
                }


            } while (isAnAvailablePosition == false);
            setNumberByIndex(idealBoard.getNumberByIndex(randomColPosition, randomRowPosition),randomColPosition,randomRowPosition);
            hints--;
            return (new int[]{randomColPosition,randomRowPosition});
        }



    }

}
