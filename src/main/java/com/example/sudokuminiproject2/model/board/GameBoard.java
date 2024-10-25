package com.example.sudokuminiproject2.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * The {@code GameBoard} class represents a 6x6 Sudoku-like game board. It manages
 * the current state of the game, including the player's guesses, mistakes, hints, and more.
 * It provides methods to handle game logic, including checking if the player has won,
 * setting initial hints, and managing mistakes.
 * <p>
 * The class also includes an inner {@link Hint} class that manages the game's hint system.
 * </p>
 *
 * @author Maria Juliana Saavedra
 * @author Libardo Alejandro Quintero
 * @version 1.0
 * @see Hint
 * @see BoardAdapter
 */
public class GameBoard extends BoardAdapter {

    private List<List<Integer>> gameBoard = new ArrayList<>(6);
    private List<List<Integer>> hintBoard = new ArrayList<>(6);
    private List<List<Integer>> mistakesBoard = new ArrayList<>(6);
    private List<List<Integer>> initialHintsBoard = new ArrayList<>(6);
    private Stack<List<Integer>> stackList = new Stack<>();
    private Board idealBoard;
    private int hints;
    private boolean isGameOver;
    private int mistakeCount = 0;

    /**
     * Constructor for the GameBoard class. Initializes the game state, boards, and hints.
     *
     * @param idealBoard The ideal solution board used for validating player guesses.
     */
    public GameBoard(Board idealBoard) {
        hints = 5;
        isGameOver = false;
        this.idealBoard = idealBoard;
        for (int i = 0; i < 6; i++) {
            List<Integer> row = new ArrayList<>(6);
            List<Integer> hintRow = new ArrayList<>(6);
            List<Integer> mistakeRow = new ArrayList<>(6);
            List<Integer> initialHintsRow = new ArrayList<>(6);
            for (int x = 0; x < 6; x++) {
                row.add(0);
                hintRow.add(0);
                mistakeRow.add(0);
                initialHintsRow.add(0);
            }
            gameBoard.add(row);
            hintBoard.add(hintRow);
            mistakesBoard.add(mistakeRow);
            initialHintsBoard.add(initialHintsRow);
        }
    }

    /**
     * Retrieves the stack of moves made during the game.
     *
     * @return The stack containing previous moves.
     */
    public Stack<List<Integer>> getStackList() {
        return stackList;
    }

    /**
     * Sets the game over state.
     *
     * @param state A boolean indicating if the game is over.
     */
    public void setGameOver(boolean state) {
        isGameOver = state;
    }

    /**
     * Pushes a move to the stack for undo functionality.
     *
     * @param previousNumber The previous number that was in the cell.
     * @param row The row index of the move.
     * @param col The column index of the move.
     */
    public void pushToStack(int previousNumber, int row, int col) {
        List<Integer> list = new ArrayList<>(4);
        list.add(previousNumber);
        list.add(row);
        list.add(col);
        stackList.push(list);
    }

    /**
     * Retrieves the mistakes board, which tracks incorrect guesses.
     *
     * @return The mistakes board.
     */
    public List<List<Integer>> getMistakesBoard() {
        return mistakesBoard;
    }

    /**
     * Retrieves the current state of the game board.
     *
     * @return The game board.
     */
    public List<List<Integer>> getGameBoard() {
        return gameBoard;
    }

    /**
     * Returns the total number of mistakes made by the player.
     *
     * @return The number of mistakes.
     */
    public int getMistakeCount() {
        return mistakeCount;
    }

    /**
     * Retrieves the initial hints board.
     *
     * @return The board containing the initial hints placed at the start of the game.
     */
    public List<List<Integer>> getInitialHintsBoard() {
        return initialHintsBoard;
    }

    /**
     * Marks a mistake at the given board position.
     *
     * @param column The column index where the mistake occurred.
     * @param row The row index where the mistake occurred.
     */
    public void setMistakes(int column, int row) {
        if (mistakesBoard.get(column).get(row) == 0) {
            mistakesBoard.get(column).set(row, 1);
            mistakeCount++;
        }
    }

    /**
     * Fixes a mistake at the given board position.
     *
     * @param column The column index where the mistake is being corrected.
     * @param row The row index where the mistake is being corrected.
     */
    public void setMistakesFix(int column, int row) {
        if (mistakesBoard.get(column).get(row) == 1) {
            mistakesBoard.get(column).set(row, 0);
            mistakeCount--;
        }
    }

    /**
     * Displays the stack of moves in a formatted string.
     *
     * @return A string representation of the stack.
     */
    public String showStack() {
        StringBuilder finalMessage = new StringBuilder();
        if (stackList.isEmpty()) {
            return "The stack is empty.\n";
        }
        for (int i = stackList.size() - 1; i >= 0; i--) {
            List<Integer> list = stackList.get(i);
            for (Integer number : list) {
                finalMessage.append(number).append(" ");
            }
            finalMessage.append("\n");
        }
        return finalMessage.toString();
    }

    /**
     * Displays the mistakes board in a formatted string.
     *
     * @return A string representation of the mistakes board.
     */
    public String showMistakesBoard() {
        StringBuilder finalMessage = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                finalMessage.append(mistakesBoard.get(j).get(i)).append(" ");
            }
            finalMessage.append("\n");
        }
        return finalMessage.toString();
    }

    /**
     * Displays the initial hints board in a formatted string.
     *
     * @return A string representation of the initial hints board.
     */
    public String showInitialHintsBoard() {
        StringBuilder finalMessage = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                finalMessage.append(initialHintsBoard.get(j).get(i)).append(" ");
            }
            finalMessage.append("\n");
        }
        return finalMessage.toString();
    }

    /**
     * Checks if the player has won the game. The player wins if there are no mistakes
     * and no empty cells left on the board.
     *
     * @return {@code true} if the player has won, {@code false} otherwise.
     */
    public boolean isWinner() {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (gameBoard.get(i).get(j) == 0) {
                    count++;
                }
            }
        }
        return mistakeCount == 0 && count == 0;
    }

    /**
     * Sets initial hints by placing random hints on the board from the ideal solution board.
     */
    public void setInitialHints() {
        Random random = new Random();
        int randomRow, randomCol, number;
        for (int i = 0; i < 4; i += 3) {
            for (int j = 0; j < 6; j++) {
                do {
                    randomRow = (j / 2) * 2 + random.nextInt(2);
                    randomCol = (i / 3) * 3 + random.nextInt(3);
                } while (getNumberByIndex(randomCol, randomRow) != 0);
                number = idealBoard.getNumberByIndex(randomCol, randomRow);
                setNumberByIndex(gameBoard, number, randomCol, randomRow);
                setNumberByIndex(hintBoard, 1, randomCol, randomRow);
            }
        }
    }

    /**
     * Retrieves the number at the specified position on the game board.
     *
     * @param column The column index.
     * @param row The row index.
     * @return The number at the specified position on the board.
     */
    public int getNumberByIndex(int column, int row) {
        return gameBoard.get(column).get(row);
    }

    /**
     * Checks if the current position contains a mistake.
     *
     * @param column The column index.
     * @param row The row index.
     * @return {@code true} if the position contains a mistake, {@code false} otherwise.
     */
    public boolean isActualPositionMistake(int column, int row) {
        return mistakesBoard.get(column).get(row) == 1;
    }

    /**
     * Checks if the current position contains a hint.
     *
     * @param column The column index.
     * @param row The row index.
     * @return {@code true} if the position contains a hint, {@code false} otherwise.
     */
    public boolean isActualPositionHint(int column, int row) {
        return hintBoard.get(column).get(row) == 1;
    }

    /**
     * Checks if the given number is allowed in the box containing the specified position.
     *
     * @param verifiedNumber The number to check.
     * @param column The column index.
     * @param row The row index.
     * @return {@code true} if the number is allowed, {@code false} otherwise.
     */
    public boolean isNumberByBoxAllowed(int verifiedNumber, int column, int row) {
        int startRow = (row / 2) * 2;
        int startCol = (column / 3) * 3;
        for (int i = startCol; i < startCol + 3; i++) {
            for (int j = startRow; j < startRow + 2; j++) {
                if (gameBoard.get(i).get(j) == verifiedNumber && column != i && row != j) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the given number is allowed in the specified column.
     *
     * @param randomNumber The number to check.
     * @param column The column index.
     * @param row The row index.
     * @return {@code true} if the number is allowed, {@code false} otherwise.
     */
    public boolean isNumberByColumnAllowed(int randomNumber, int column, int row) {
        for (int j = 0; j < 6; j++) {
            if (gameBoard.get(column).get(j) == randomNumber && row != j) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given number is allowed in the specified row.
     *
     * @param randomNumber The number to check.
     * @param column The column index.
     * @param row The row index.
     * @return {@code true} if the number is allowed, {@code false} otherwise.
     */
    public boolean isNumberByRowAllowed(int randomNumber, int column, int row) {
        for (int i = 0; i < 6; i++) {
            if (gameBoard.get(i).get(row) == randomNumber && column != i) {
                return false;
            }
        }
        return true;
    }

    /**
     * Displays the current state of the game board in a formatted string.
     *
     * @return A string representation of the game board.
     */
    public String showBoard() {
        StringBuilder finalMessage = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                finalMessage.append(gameBoard.get(j).get(i)).append("  ");
            }
            finalMessage.append("\n");
        }
        return finalMessage.toString();
    }

    /**
     * Resets the game board for a new game by clearing all the cells.
     */
    public void restartBoardForNewGame() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                gameBoard.get(j).set(i, 0);
            }
        }
    }

    /**
     * Inner class responsible for managing the hint system within the game.
     * It provides methods to offer hints or fix mistakes using hints.
     * @author Maria Juliana Saavedra
     * @author Libardo Alejandro Quintero
     * @version 1.0
     * @see GameBoard
     */
    public class Hint {

        /**
         * Retrieves the remaining number of hints available.
         *
         * @return The number of remaining hints.
         */
        public int getHints() {
            return hints;
        }

        /**
         * Set the number of hints.
         *
         * @param number
         */
        public void setHints(int number) {
             hints=number;
        }

        /**
         * Determines if the player can use hints based on the game state.
         *
         * @return {@code true} if hints can be used, {@code false} otherwise.
         */
        public boolean canUseHints() {
            return hints != 0 && !isGameOver;
        }

        /**
         * Attempts to provide a hint by fixing a mistake.
         *
         * @return An array containing the number, column, and row for the hint, or {@code -1} if no hint is available.
         */
        public int[] secondHardTryingHint() {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    if (isActualPositionMistake(col, row) && !isActualPositionHint(col, row)) {
                        for (int num = 1; num <= 6; num++) {
                            if (isNumberByBoxAllowed(num, col, row)
                                    && isNumberByColumnAllowed(num, col, row)
                                    && isNumberByRowAllowed(num, col, row)) {
                                setNumberByIndex(mistakesBoard, 0, col, row);
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
                    if (getNumberByIndex(col, row) != idealBoard.getNumberByIndex(col, row)) {
                        if (isActualPositionMistake(col, row)) {
                            mistakeCount--;
                            setNumberByIndex(mistakesBoard, 0, col, row);
                        }
                        hints--;
                        return new int[]{idealBoard.getNumberByIndex(col, row), col, row};
                    }
                }
            }
            return new int[]{-1};
        }

        /**
         * Provides a random hint by finding an empty cell and placing a number there.
         *
         * @return An array containing the number, column, and row for the hint, or {@code -1} if no hint is available.
         */
        public int[] randomHint() {
            Random rand = new Random();
            int randomRowPosition = rand.nextInt(6);
            int randomColPosition = rand.nextInt(6);
            int hintsAttempts = 0;
            int number;
            do {
                hintsAttempts++;
                if (getNumberByIndex(randomColPosition, randomRowPosition) != 0) {
                    if (randomColPosition == 5) {
                        randomColPosition = 0;
                        if (randomRowPosition == 5) {
                            randomRowPosition = 0;
                        } else {
                            randomRowPosition++;
                        }
                    } else {
                        randomColPosition++;
                    }
                } else {
                    number = idealBoard.getNumberByIndex(randomColPosition, randomRowPosition);
                    if (isNumberByRowAllowed(gameBoard, number, randomColPosition, randomRowPosition)
                            && isNumberByColumnAllowed(gameBoard, number, randomColPosition, randomRowPosition)
                            && isNumberByBoxAllowed(gameBoard, number, randomColPosition, randomRowPosition)) {
                        hints--;
                        return new int[]{number, randomColPosition, randomRowPosition};
                    }
                }
            } while (hintsAttempts <= 36);
            return new int[]{-1};
        }
    }
}
