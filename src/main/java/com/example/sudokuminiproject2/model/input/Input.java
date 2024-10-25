package com.example.sudokuminiproject2.model.input;

/**
 * The Input class provides methods to handle and validate user input for the Sudoku game.
 * It implements the {@link IInput} interface.
 *
 * @author Maria Juliana Saavedra
 * @author Libardo Alejandro Quintero
 * @version 1.0
 * @see IInput
 */
public class Input implements IInput {

    /**
     * Handles the user input. Currently, this method has no implementation.
     *
     * @see #input()
     */
    public void input() {
        // No implementation yet
    }

    /**
     * Checks if the length of the entered string is valid.
     *
     * @param entryNumber The string entered by the user.
     * @return {@code true} if the length is 1, {@code false} otherwise.
     */
    public boolean isValidLength(String entryNumber) {
        return entryNumber.length() == 1;
    }

    /**
     * Checks if the entered string is a valid number between 1 and 6.
     *
     * @param entryNumber The string entered by the user.
     * @return {@code true} if the string represents a valid number between 1 and 6, {@code false} otherwise.
     * @throws NumberFormatException If the string cannot be parsed as an integer.
     */
    public boolean isValidNumber(String entryNumber) {
        try {
            int number = Integer.parseInt(entryNumber);
            if (number >= 1 && number <= 6) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
}
