package com.example.sudokuminiproject2.model.board;

import java.util.List;

/**
 * The abstract class BoardAdapter provides a default implementation of the {@link IBoard} interface.
 * This class acts as an adapter, allowing other classes to extend its functionality without
 * needing to implement all the methods from {@link IBoard}.
 *
 * @author Maria Juliana Saavedra
 * @author Libardo Alejandro Quintero
 * @version 1.0
 * @see IBoard
 */
public abstract class BoardAdapter implements IBoard {

    /**
     * Returns the number located at a specific position on the Sudoku board.
     * This default implementation always returns 0.
     *
     * @param column The column of the board.
     * @param row The row of the board.
     * @return The number at the specified position (always 0 in this implementation).
     * @see IBoard#getNumberByIndex(int, int)
     */
    @Override
    public int getNumberByIndex(int column, int row) {
        return 0;
    }

    /**
     * Displays the current state of the board.
     * This default implementation returns an empty string.
     *
     * @return A string representation of the board (empty in this implementation).
     * @see IBoard#showBoard()
     */
    @Override
    public String showBoard() {
        return "";
    }
}
