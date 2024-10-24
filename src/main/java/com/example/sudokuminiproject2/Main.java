package com.example.sudokuminiproject2;

import com.example.sudokuminiproject2.model.board.Board;
import com.example.sudokuminiproject2.model.board.GameBoard;
import com.example.sudokuminiproject2.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main class serves as the entry point of the Sudoku application.
 * It extends the JavaFX Application class to launch the graphical user interface.
 * The {@code start} method initializes the main window and generates the game board.
 *
 * @author Maria Juliana Saavedra
 * @author Libardo Alejandro Quintero
 * @version 1.0
 * @see javafx.application.Application
 * @see com.example.sudokuminiproject2.model.board.Board
 * @see com.example.sudokuminiproject2.model.board.GameBoard
 * @see com.example.sudokuminiproject2.view.WelcomeStage
 */
public class Main extends Application {

    /**
     * The main method of the application, responsible for launching the GUI.
     *
     * @param args Command-line arguments.
     * @see #start(Stage)
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This overridden method starts the JavaFX application lifecycle.
     * It initializes the WelcomeStage and the game board. It also prints the state of
     * the current board, the ideal board, and a board with initial hints.
     *
     * @param primaryStage The main window where the interface will be displayed.
     * @throws IOException If an error occurs while loading resources or necessary files.
     * @see WelcomeStage
     * @see GameBoard
     * @see Board
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        WelcomeStage.getInstance();

        Board actualBoard = new Board();

        GameBoard gameBoard = new GameBoard(actualBoard);

        System.out.println("GAME BOARD");
        System.out.println(gameBoard.showBoard());
        System.out.println("IDEAL BOARD");
        System.out.println(actualBoard.showIdealGame(actualBoard.getBoard()));

        gameBoard.setInitialHints();
        System.out.println("BOARD WITH HINTS");
        System.out.println(gameBoard.showBoard());
    }
}
