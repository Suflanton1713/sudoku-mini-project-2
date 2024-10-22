package com.example.sudokuminiproject2.model.board;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board extends BoardAdapter {
    List<List<Integer>> board = new ArrayList<>(6);
    public Board(){
        for(int i=0; i<6; i++){
            List<Integer> row = new ArrayList<>(6);
            for (int x = 0; x < 6; x++) {
                row.add(0);
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

        System.out.println("The number given is "+ randomNumber);
        return randomNumber;
    }


    public void correctingBoard(int actualColumn){
        int numbersAllowed = 0;
        int row = 0;

        while(numbersAllowed != 6){
            if(isNumberByRowAllowed(board,board.get(actualColumn).get(row), actualColumn, row) && isNumberByBoxAllowed(board,board.get(actualColumn).get(row), actualColumn, row)){
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

    public int getNumberByIndex(int column, int row){  return board.get(column).get(row);  };

    public List<List<Integer>> getBoard(){
        return board;
    }

}

