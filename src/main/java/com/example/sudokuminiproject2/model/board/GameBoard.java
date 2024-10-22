package com.example.sudokuminiproject2.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class GameBoard extends BoardAdapter{

    private List<List<Integer>> gameBoard = new ArrayList<>(6);
    private List<List<Integer>> mistakesBoard = new ArrayList<>(6);
    private Stack<List<Integer>> listHistory = new Stack<>();
    private Board idealBoard;
    private int hints;
    private boolean isGameOver;
    private int mistakeCount = 0;

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

        for(int i=0; i<6; i++){
            List<Integer> row = new ArrayList<>(6);
            for (int x = 0; x < 6; x++) {
                row.add(0);
            }
            mistakesBoard.add(row);
        }
    }
    public List<List<Integer>> getGameBoard() {
        return gameBoard;
    }

    public Stack<List<Integer>> getListHistory() {
        return listHistory;
    }

    public void pushList(int number, int row, int col){
        List<Integer> list = new ArrayList<>(3);
        list.add(number);
        list.add(row);
        list.add(col);
        listHistory.push(list);
    }

    public int getMistakeCount() {
        return mistakeCount;
    }

    public List<List<Integer>> getMistakesBoard() {
        return mistakesBoard;
    }

    public void setMistakes(int column, int row) {
        System.out.println("antes de mistake");
        System.out.println(showMistakesBoard());
        if (mistakesBoard.get(column).get(row) == 0) {
            mistakesBoard.get(column).set(row, 1);
            mistakeCount++;
            System.out.println("entro aquii si hay mistake");
            System.out.println(showMistakesBoard());
        }
    }
    public void setMistakesFix(int column, int row) {
        System.out.println("antes de fix");
        System.out.println(showMistakesBoard());
        if (mistakesBoard.get(column).get(row) == 1) {
            mistakesBoard.get(column).set(row, 0);
            mistakeCount--;
            System.out.println("entro aquii error fix");
            System.out.println(showMistakesBoard());
        }
    }
    public String showMistakesBoard(){
        String finalMessage = "";
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                finalMessage = finalMessage + mistakesBoard.get(j).get(i) + " ";
            }
            finalMessage = finalMessage + "\n";
        }
        return finalMessage;
    }

    public void setNumberByIndex(int number, int column, int row){
        gameBoard.get(column).set(row, number);
    }

    public boolean isWinner(){
        int count =0;
        for(int i=0; i<6;i++){
            for(int j=0; j<6; j++){
                if (gameBoard.get(i).get(j) == 0){
                    count++;
                }
            }
        }
        if(mistakeCount ==0 &&count==0){
            return true;
        }
        return false;
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
    public void restartBoardForNewGame(){
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                gameBoard.get(j).set(i, 0);
            }
        }
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
