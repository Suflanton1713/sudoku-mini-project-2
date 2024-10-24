package com.example.sudokuminiproject2.model.board;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Represents a 6x6 Sudoku board and provides functionality to initialize and manage the board.
 * This class generates a valid Sudoku board with randomized numbers and includes methods for
 * checking and correcting the validity of the board.
 *
 * @author María Juliana Saavedra
 * @author Libardo Alejandro Quintero
 * @version 1.0
 * @see BoardAdapter
 */
public class Board extends BoardAdapter {

    /**
     * A 6x6 list representing the Sudoku board. Each element of the list is a row containing integers.
     */
    List<List<Integer>> board = new ArrayList<>(6);

    /**
     * Constructs the board and initializes it with random valid numbers.
     * This constructor generates the board by filling each row with random numbers and
     * then ensuring that the board is valid by calling the `correctingBoard` method.
     */
    public Board() {
        for (int i = 0; i < 6; i++) {
            List<Integer> row = new ArrayList<>(6);
            for (int x = 0; x < 6; x++) {
                row.add(0);
            }
            board.add(row);
        }
        int allowedNumbers[] = new int[6];
        for (int i = 0; i < 6; i++) {
            Arrays.setAll(allowedNumbers, n -> n + 1);
            for (int j = 0; j < 6; j++) {
                board.get(i).set(j, randomNumberGenerator(allowedNumbers));
            }
            correctingBoard(i);
        }
    }

    /**
     * Generates a random number from the list of allowed numbers and removes it from the list.
     *
     * @param allowedNumbers the list of allowed numbers to choose from
     * @return the generated random number
     * @see #correctingBoard(int)
     */
    public int randomNumberGenerator(int[] allowedNumbers) {
        Random random = new Random();
        int randomNumber = 0;
        int randomIndex = 0;
        randomIndex = random.nextInt(allowedNumbers.length);
        do {
            randomNumber = (allowedNumbers[randomIndex] != 0 ? allowedNumbers[randomIndex] : 0);
            if (randomNumber == 0) {
                randomIndex = (randomIndex == allowedNumbers.length - 1 ? 0 : randomIndex + 1);
            }
        } while (randomNumber == 0);

        allowedNumbers[randomIndex] = 0;

        return randomNumber;
    }

    /**
     * Corrects the board for the specified column by ensuring that all rows are valid
     * based on the Sudoku rules for rows and boxes.
     * If the numbers in the current column do not satisfy the rules, new numbers are generated until they do.
     *
     * @param actualColumn the column being corrected
     * @see #randomNumberGenerator(int[])
     */
    public void correctingBoard(int actualColumn) {
        int numbersAllowed = 0;
        int row = 0;

        while (numbersAllowed != 6) {
            if (isNumberByRowAllowed(board, board.get(actualColumn).get(row), actualColumn, row) &&
                    isNumberByBoxAllowed(board, board.get(actualColumn).get(row), actualColumn, row)) {
                numbersAllowed++;
                row++;
            } else {
                int allowedNumbers[] = new int[6];
                Arrays.setAll(allowedNumbers, n -> n + 1);
                for (int j = 0; j < 6; j++) {
                    board.get(actualColumn).set(j, randomNumberGenerator(allowedNumbers));
                }
                row = 0;
                numbersAllowed = 0;
            }
        }
    }

    /**
     * Returns the number located at the specified position on the board.
     *
     * @param column the column index
     * @param row the row index
     * @return the number at the specified position on the board
     */
    public int getNumberByIndex(int column, int row) {
        return board.get(column).get(row);
    }

    /**
     * Returns the entire 6x6 board as a list of lists.
     *
     * @return the 6x6 board
     */
    public List<List<Integer>> getBoard() {
        return board;
    }

}
