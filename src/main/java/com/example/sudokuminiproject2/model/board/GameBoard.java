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

    public boolean isNumberByBoxAllowed(int verifiedNumber, int column, int row) {
        System.out.println("Check by box Started");
        int startRow = (row / 2);
        startRow = startRow * 2;// Fila de inicio de la subcuadrícula
        int startCol = (column / 3);
        startCol = startCol *3;// Columna de inicio de la subcuadrícula
        System.out.println("startRow is " + startRow + " and startCol is " + startCol);
        System.out.println("row " + row + " and col is " + column);

        // Iterar sobre la subcuadrícula de 2 filas por 3 columnas
        for (int i = startCol; i < startCol + 3; i++) {
            for (int j = startRow ; j < startRow + 2; j++) {
                // Verificar si el número ya está en la subcuadrícula
                System.out.println("Row is " + i);
                System.out.println("Col is " + j);
                System.out.println(gameBoard.get(i).get(j) + " == " + verifiedNumber);
                if (gameBoard.get(i).get(j) == verifiedNumber && column != i && row != j) {
                    System.out.println("yes bad");
                    return false; // Número ya presente
                }
            }
        }
        System.out.println("no gud");
        return true; // Número permitido
    }

    public boolean isNumberByColumnAllowed(int randomNumber, int column, int row){
        for(int j=0; j<6; j++){
            if(gameBoard.get(column).get(j) == randomNumber && row != j ){
                return false;
            }
        }
        return true;

    }

    public boolean isNumberByRowAllowed(int randomNumber, int column, int row){
        for(int i=0; i<6; i++){
            if(gameBoard.get(i).get(row) == randomNumber && column != i ){
                return false;
            }
        }
        return true;

    }

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
            int attemptsToHint = 0;
            boolean hintFound = false;
            int randomNumber;
            int maxAttempts = 40;

            // First, search for an empty position
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    if (getNumberByIndex(col, row) == 0) {  // Empty cell
                        // Try to find a valid number for this position
                        for (int num = 1; num <= 6; num++) {
                            hintFound = isNumberByBoxAllowed(num, col, row)
                                    && isNumberByColumnAllowed(num, col, row)
                                    && isNumberByRowAllowed(num, col, row);
                            if (hintFound) {
                                hints--;
                                setNumberByIndex(num, col, row);
                                return new int[]{num, col, row};
                            }
                        }
                    }
                    attemptsToHint++;
                    if (attemptsToHint >= maxAttempts) {
                        break;  // Stop searching if the maximum number of attempts is reached
                    }
                }
            }

            // If no empty position was found, start revisiting filled positions
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    if (getNumberByIndex(col, row) != 0) {  // Filled cell
                        int currentValue = getNumberByIndex(col, row);
                        // Check if any other number can be placed here
                        for (int num = 1; num <= 6; num++) {
                            if (num != currentValue && isNumberByBoxAllowed(num, col, row)
                                    && isNumberByColumnAllowed(num, col, row)
                                    && isNumberByRowAllowed(num, col, row)) {
                                hints--;
                                setNumberByIndex(num, col, row);
                                return new int[]{num, col, row};
                            }
                        }
                    }
                }
            }

            // If no hint was found, return failure
            return new int[]{-1};
        }



//        public int[] randomHint() {
//            Random rand = new Random();
//            int randomRowPosition = -1;
//            int randomColPosition = -1;
//            randomRowPosition = rand.nextInt(6);
//            randomColPosition = rand.nextInt(6);
//            boolean isAnAvailablePosition = false;
//            do {
//
//                System.out.println("The hint col is " + randomColPosition);
//                System.out.println("The hint row is " + randomRowPosition);
//                if(getNumberByIndex(randomColPosition, randomRowPosition) == idealBoard.getNumberByIndex(randomColPosition, randomRowPosition) ){
//
//                    //The following code is an algorithm that allows to search into cols and rows for a space with hints available
//                    //It goes by columns until it reaches the sixth column, then he jumps off the actual row towards the next row and starts again in the first column
//                    //Makes the same thing with the rows.
//                    if(randomColPosition==5){
//                        randomColPosition= 0;
//                        if(randomRowPosition==5){
//                            randomRowPosition = 0;
//                        }else{
//                            randomRowPosition++;
//                            System.out.println("We moved the row " + randomRowPosition);
//                        }
//                    }else{
//                        randomColPosition++;
//                        System.out.println("We moved the col " + randomColPosition);
//                    }
//
//
//                }else{
//                    isAnAvailablePosition = true;
//                }
//
//
//            } while (isAnAvailablePosition == false);
//            setNumberByIndex(idealBoard.getNumberByIndex(randomColPosition, randomRowPosition),randomColPosition,randomRowPosition);
//            hints--;
//            return (new int[]{randomColPosition,randomRowPosition});
//        }

    }

}
