package com.example.sudokuminiproject2.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard extends BoardAdapter{

    private List<List<Integer>> gameBoard = new ArrayList<>(6);
    private List<List<Integer>> hintBoard = new ArrayList<>(6);
    private List<List<Integer>> mistakes = new ArrayList<>(6);
    private Board idealBoard;
    private int hints;
    private boolean isGameOver;
    private int mistakeCount = 0;

    public GameBoard(Board idealBoard) {
        hints = 5;
        isGameOver = false;
        this.idealBoard = idealBoard;
        for (int i = 0; i < 6; i++) {
            List<Integer> row = new ArrayList<>(6);
            List<Integer> hintRow = new ArrayList<>(6);
            List<Integer> mistakeRow = new ArrayList<>(6);
            for (int x = 0; x < 6; x++) {
                row.add(0);
                hintRow.add(0);
                mistakeRow.add(0);

            }
            gameBoard.add(row);
            hintBoard.add(hintRow);
            mistakes.add(mistakeRow);
        }
    }

    public List<List<Integer>> getGameBoard() {
        return gameBoard;
    }

    public int getMistakeCount() {
        return mistakeCount;
    }

    public void setMistakes(int column, int row) {
//        System.out.println("antes de mistake");
        System.out.println(showMistakes());
        if (mistakes.get(column).get(row) == 0) {
            mistakes.get(column).set(row, 1);
            mistakeCount++;
//            System.out.println("entro aquii si hay mistake");
            System.out.println(showMistakes());
        }


    }
    public void setMistakesFix(int column, int row) {
//        System.out.println("antes de fix");
        System.out.println(showMistakes());
        if (mistakes.get(column).get(row) == 1) {
            mistakes.get(column).set(row, 0);
            mistakeCount--;
//            System.out.println("entro aquii error fix");
            System.out.println(showMistakes());
        }
    }
    public String showMistakes(){
        String finalMessage = "";
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                finalMessage = finalMessage + mistakes.get(j).get(i) + " ";
            }
            finalMessage = finalMessage + "\n";
        }
        return finalMessage;
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

        Random random = new Random();
        int randomRow, randomCol, number;
        for(int i = 0; i<4; i+=3){
            for(int j = 0; j<6; j++){
                do{
                    randomRow = (j / 2);
                    randomRow = randomRow *2 +(random.nextInt(2));
                    randomCol = (i / 3);
                    randomCol = randomCol * 3 + (random.nextInt(3));


                }while(getNumberByIndex(randomCol, randomRow) != 0);
                number = idealBoard.getNumberByIndex(randomCol, randomRow);
                setNumberByIndex(gameBoard,number ,randomCol, randomRow);
                setNumberByIndex(hintBoard,1, randomCol, randomRow);

            }
        }

    }

    public int getNumberByIndex(int column, int row){  return gameBoard.get(column).get(row);  };

    public boolean isActualPositionMistake(int column, int row){  return mistakes.get(column).get(row) == 1;  };

    public boolean isActualPositionHint(int column, int row){  return hintBoard.get(column).get(row) == 1;  };

    public boolean isNumberByBoxAllowed(int verifiedNumber, int column, int row) {

        int startRow = (row / 2);
        startRow = startRow * 2;// Fila de inicio de la subcuadrícula
        int startCol = (column / 3);
        startCol = startCol *3;// Columna de inicio de la subcuadrícula

        // Iterar sobre la subcuadrícula de 2 filas por 3 columnas
        for (int i = startCol; i < startCol + 3; i++) {
            for (int j = startRow ; j < startRow + 2; j++) {
                // Verificar si el número ya está en la subcuadrícula

                System.out.println(gameBoard.get(i).get(j) + " == " + verifiedNumber);
                if (gameBoard.get(i).get(j) == verifiedNumber && column != i && row != j) {
                    return false; // Número ya presente
                }
            }
        }

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
    public void restartBoardForNewGame(){
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                gameBoard.get(j).set(i, 0);
            }
        }
    }



    public class Hint {


        public int getHints() {
            return hints;
        }

        public boolean canUseHints() {
            return hints != 0 && isGameOver;
        }

        public int[] secondHardTryingHint() {

            // If no empty position was found, start revisiting filled positions
            System.out.println(mistakes);
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    System.out.println("The position has mistake " + col + " " + row + " " + (isActualPositionMistake(col, row) && !isActualPositionHint(col, row)));
                    if (isActualPositionMistake(col, row) && !isActualPositionHint(col, row)) {
                        for (int num = 1; num <= 6; num++) {
                            if (isNumberByBoxAllowed(num, col, row)
                                    && isNumberByColumnAllowed(num, col, row)
                                    && isNumberByRowAllowed(num, col, row)) {
                                setNumberByIndex(gameBoard, num, col, row);
                                setNumberByIndex(mistakes, 0, col, row);
                                mistakeCount--;
                                hints--;
                                return new int[]{num, col, row};
                            }
                        }
                    }
                }
            }

            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    if(getNumberByIndex(col, row) != idealBoard.getNumberByIndex(col, row)){
                        if(isActualPositionMistake(col, row)){
                            mistakeCount--;
                            setNumberByIndex(mistakes, 0, col, row);
                        }
                        setNumberByIndex(gameBoard, idealBoard.getNumberByIndex(col, row), col, row);
                        hints--;
                        return new int[]{idealBoard.getNumberByIndex(col, row), col, row};
                    }
                }
            }
            // If no hint was found, return failure
            return new int[]{-1};
    }


        public int[] randomHint() {
            Random rand = new Random();
            int randomRowPosition = -1;
            int randomColPosition = -1;
            randomRowPosition = rand.nextInt(6);
            randomColPosition = rand.nextInt(6);
            int hintsAttempts = 0;
            int number;
            do {
                hintsAttempts++;
                System.out.println("row "+ randomRowPosition + " col " + randomColPosition);
                if(getNumberByIndex(randomColPosition, randomRowPosition) != 0){
                    if(randomColPosition==5){
                        randomColPosition= 0;
                        if(randomRowPosition==5){
                            randomRowPosition = 0;
                        }else{
                            randomRowPosition++;
                        }
                    }else{
                        randomColPosition++;
                    }

                }else{
                    number = idealBoard.getNumberByIndex(randomColPosition, randomRowPosition);
                    System.out.println("Selected number "+ number);
                    if(isNumberByRowAllowed(gameBoard, number, randomColPosition, randomRowPosition)
                    && isNumberByColumnAllowed(gameBoard, number, randomColPosition, randomRowPosition)
                    && isNumberByBoxAllowed(gameBoard, number, randomColPosition, randomRowPosition)){
                        setNumberByIndex(gameBoard, number,randomColPosition,randomRowPosition);
                        hints--;
                        return new int[]{number,randomColPosition,randomRowPosition};
                    }

                }

            } while (hintsAttempts <= 36);


                return (new int[]{-1});
                }


        }

    }


