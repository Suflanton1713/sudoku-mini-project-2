package com.example.sudokuminiproject2.model.board;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board extends BoardAdapter {

    public Board(){
        for(int i=0; i<6; i++){
            List<Integer> row = new ArrayList<>(6);
            for (int x = 0; x < 6; x++) {
                row.add(-1);
            }

            board.add(row);
        }
        int allowedNumbers[] = new int[6];
        for(int i = 0; i<6; i++){
            Arrays.setAll(allowedNumbers, n -> n+1);
            System.out.println("Created new Array of random numbers");
            for(int j=0; j<6; j++){
                System.out.println("Rellenando columna " +i);
                board.get(i).set(j, randomNumberGenerator(allowedNumbers));


            }
            System.out.println(showBoard());
            System.out.println("Correcting " +i +" column");
            correctingBoard(i);
            System.out.println("Correctly Done");
            System.out.println(showBoard());


        }


    }

    public int randomNumberGenerator(int[] allowedNumbers) {
        Random random = new Random();
        int randomNumber = 0;
        int randomIndex = 0;
        randomIndex = random.nextInt(allowedNumbers.length);

        do {
            System.out.println("The random index is " + randomIndex);
            randomNumber = (allowedNumbers[randomIndex] != 0 ? allowedNumbers[randomIndex] : 0);
            if(randomNumber == 0){
                randomIndex = (randomIndex == allowedNumbers.length-1 ? 0 : randomIndex + 1);
            }
            System.out.println("The number selected is " + randomNumber);

        } while(randomNumber == 0);

        allowedNumbers[randomIndex] = 0;
        // - 1, because at the end of the do-while we add 1 to randomIndex even if that number is allowed
        System.out.println("The number given is "+ randomNumber);
        return randomNumber;
    }

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
                System.out.println(board.get(i).get(j) + " == " + verifiedNumber);
                if (board.get(i).get(j) == verifiedNumber && column != i && row != j) {
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
                if(board.get(column).get(j) == randomNumber && row != j ){
                    return false;
                }
        }
        return true;

    }

    public boolean isNumberByRowAllowed(int randomNumber, int column, int row){
        for(int i=0; i<6; i++){
            if(board.get(i).get(row) == randomNumber && column != i ){
                return false;
            }
        }
        return true;

    }

    public void correctingBoard(int actualColumn){
        int numbersAllowed = 0;
        int row = 0;

        while(numbersAllowed != 6){
            if(isNumberByRowAllowed(board.get(actualColumn).get(row), actualColumn, row) && isNumberByBoxAllowed(board.get(actualColumn).get(row), actualColumn, row)){
                numbersAllowed++;
                row ++;
            }else{
                int allowedNumbers[] = new int[6];
                Arrays.setAll(allowedNumbers, n -> n +1);
                for(int j=0; j<6; j++) {
                    board.get(actualColumn).set(j, randomNumberGenerator(allowedNumbers));
                }
                row = 0;
                numbersAllowed = 0;
                }
            }
        }

        public String showBoard(){
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

