package com.example.sudokuminiproject2.model.board;

import java.util.ArrayList;
import java.util.List;

public interface IBoard {


    int getNumberByIndex(int column, int row);

    String showBoard();

    default boolean isNumberByColumnAllowed(List<List<Integer>> board , int numberSelected, int column, int row){
        for(int j=0; j<6; j++){
            if(board.get(column).get(j) == numberSelected && row != j ){
                return false;
            }
        }
        return true;
    }
    default boolean isNumberByRowAllowed(List<List<Integer>> board , int numberSelected, int column, int row){
        for(int i=0; i<6; i++){
            if(board.get(i).get(row) == numberSelected && column != i ){
                return false;
            }
        }
        return true;
    }

    default void setNumberByIndex(List<List<Integer>> board, int number, int column, int row){
        System.out.println(board);
        board.get(column).set(row, number);
        System.out.println(board);
    }

    default boolean isNumberByBoxAllowed(List<List<Integer>> board, int numberSelected, int column, int row){
//        System.out.println("Check by box Started");
        int startRow = (row / 2);
        startRow = startRow * 2;// Fila de inicio de la subcuadrícula
        int startCol = (column / 3);
        startCol = startCol *3;// Columna de inicio de la subcuadrícula
//        System.out.println("startRow is " + startRow + " and startCol is " + startCol);
//        System.out.println("row " + row + " and col is " + column);

        // Iterar sobre la subcuadrícula de 2 filas por 3 columnas
        for (int i = startCol; i < startCol + 3; i++) {
            for (int j = startRow ; j < startRow + 2; j++) {
                // Verificar si el número ya está en la subcuadrícula
//                System.out.println("Row is " + i);
//                System.out.println("Col is " + j);
//                System.out.println(board.get(i).get(j) + " == " + numberSelected);
                if (board.get(i).get(j) == numberSelected && column != i && row != j) {
//                    System.out.println("yes bad");
                    return false; // Número ya presente
                }
            }
        }
//        System.out.println("no gud");
        return true; // Número permitido
    }

    default String showIdealGame(List<List<Integer>> board ){
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