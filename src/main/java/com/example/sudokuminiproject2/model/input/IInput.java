package com.example.sudokuminiproject2.model.input;

/**
 * The IInput interface defines the methods for validating user input in the Sudoku game.
 * Classes that implement this interface must provide functionality to check the validity
 * of the input length and the input number.
 *
 * @author Maria Juliana Saavedra
 * @author Libardo Alejandro Quintero
 * @version 1.0
 */
public interface IInput {

    /**
     * Validates if the length of the entered string is correct.
     *
     * @param entryNumber The string entered by the user.
     * @return {@code true} if the length is valid, {@code false} otherwise.
     */
    boolean isValidLength(String entryNumber);

    /**
     * Validates if the entered string is a valid number.
     *
     * @param entryNumber The string entered by the user.
     * @return {@code true} if the string represents a valid number, {@code false} otherwise.
     */
    boolean isValidNumber(String entryNumber);
}
