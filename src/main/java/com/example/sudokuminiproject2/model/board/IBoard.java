package com.example.sudokuminiproject2.model.board;

import java.util.List;

/**
 * The IBoard interface defines the essential methods and default implementations for handling
 * the Sudoku game board, including validation of numbers by column, row, and box, as well as
 * setting numbers and displaying the board.
 *
 * @author Maria Juliana Saavedra
 * @author Libardo Alejandro Quintero
 * @version 1.0
 */
public interface IBoard {

    /**
     * Retrieves the number located at a specific index on the Sudoku board.
     *
     * @param column The column index of the board.
     * @param row The row index of the board.
     * @return The number at the specified position.
     */
    int getNumberByIndex(int column, int row);

    /**
     * Displays the current state of the Sudoku board as a string.
     *
     * @return A string representation of the current board.
     */
    String showBoard();

    /**
     * Validates if a number is allowed in the specified column according to Sudoku rules.
     *
     * @param board The current game board represented as a list of lists.
     * @param numberSelected The number being checked.
     * @param column The column index.
     * @param row The row index.
     * @return {@code true} if the number is allowed in the column, {@code false} otherwise.
     */
    default boolean isNumberByColumnAllowed(List<List<Integer>> board, int numberSelected, int column, int row) {
        for (int j = 0; j < 6; j++) {
            if (board.get(column).get(j) == numberSelected && row != j) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates if a number is allowed in the specified row according to Sudoku rules.
     *
     * @param board The current game board represented as a list of lists.
     * @param numberSelected The number being checked.
     * @param column The column index.
     * @param row The row index.
     * @return {@code true} if the number is allowed in the row, {@code false} otherwise.
     */
    default boolean isNumberByRowAllowed(List<List<Integer>> board, int numberSelected, int column, int row) {
        for (int i = 0; i < 6; i++) {
            if (board.get(i).get(row) == numberSelected && column != i) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets a number at a specified index on the board.
     *
     * @param board The current game board represented as a list of lists.
     * @param number The number to set.
     * @param column The column index.
     * @param row The row index.
     */
    default void setNumberByIndex(List<List<Integer>> board, int number, int column, int row) {
        board.get(column).set(row, number);
    }

    /**
     * Validates if a number is allowed in a 2x3 sub-grid (box) according to Sudoku rules.
     *
     * @param board The current game board represented as a list of lists.
     * @param numberSelected The number being checked.
     * @param column The column index.
     * @param row The row index.
     * @return {@code true} if the number is allowed in the box, {@code false} otherwise.
     */
    default boolean isNumberByBoxAllowed(List<List<Integer>> board, int numberSelected, int column, int row) {
        int startRow = (row / 2) * 2; // Starting row of the sub-grid
        int startCol = (column / 3) * 3; // Starting column of the sub-grid

        // Iterating over the 2x3 sub-grid
        for (int i = startCol; i < startCol + 3; i++) {
            for (int j = startRow; j < startRow + 2; j++) {
                if (board.get(i).get(j) == numberSelected && column != i && row != j) {
                    return false; // Number already present in the sub-grid
                }
            }
        }
        return true; // Number allowed
    }

    /**
     * Displays the ideal state of the Sudoku game board as a string.
     *
     * @param board The current game board represented as a list of lists.
     * @return A string representation of the ideal game board.
     */
    default String showIdealGame(List<List<Integer>> board) {
        StringBuilder finalMessage = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                finalMessage.append(board.get(j).get(i)).append(" ");
            }
            finalMessage.append("\n");
        }
        return finalMessage.toString();
    }
}
