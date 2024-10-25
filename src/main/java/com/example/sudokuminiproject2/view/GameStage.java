package com.example.sudokuminiproject2.view;

import com.example.sudokuminiproject2.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the game stage of the Sudoku game.
 * This class manages the main game window, including the game board and controls.
 * It follows the Singleton pattern to ensure only one instance of the game stage exists at a time.
 * It also provides access to the game controller that handles game logic.
 *
 * @author María Juliana Saavedra
 * @author Libardo Alejandro Quintero
 * @version 1.0
 * @see WelcomeStage
 * @see GameController
 */
public class GameStage extends Stage {

    /**
     * The controller responsible for handling game logic and UI interactions within the game stage.
     */
    private GameController gameController;

    /**
     * Returns the game controller for managing the game logic.
     * This method provides access to the `GameController` associated with the current game stage.
     *
     * @return the {@link GameController} instance managing the game logic
     * @see GameController
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Constructs the game stage.
     * Loads the FXML layout for the game view, applies stylesheets, sets the window title and icon,
     * and maximizes the window by default. The {@link GameController} is also initialized through the FXML loader.
     *
     * @throws IOException if there is an error loading the FXML file for the game view
     * @see GameController
     */
    public GameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudokuminiproject2/game-view.fxml"));
        Parent root = loader.load();

        gameController = loader.getController();  // Initialize the controller

        setMaximized(true);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/sudokuminiproject2/styleGame.css").toExternalForm());
        setScene(scene);
        setTitle("Sudoku");
        getIcons().add(new Image(getClass().getResourceAsStream("/com/example/sudokuminiproject2/favicon.png")));
        setResizable(true);

        show();
    }

    /**
     * Holds the singleton instance of {@code GameStage}.
     * This class follows the Singleton design pattern to ensure that only one instance of the {@code GameStage} exists at any time.
     */
    private static class GameStageHolder {
        private static GameStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@code GameStage}.
     * If the instance does not already exist, it creates a new one. Otherwise, it returns the existing instance.
     *
     * @return the singleton instance of {@code GameStage}
     * @throws IOException if there is an error in creating the {@code GameStage}
     * @see #deleteInstance()
     */
    public static GameStage getInstance() throws IOException {
        GameStage.GameStageHolder.INSTANCE =
                GameStage.GameStageHolder.INSTANCE != null ?
                        GameStage.GameStageHolder.INSTANCE : new GameStage();
        return GameStage.GameStageHolder.INSTANCE;
    }

    /**
     * Deletes the singleton instance of {@code GameStage}.
     * This method closes the current instance and sets it to null, allowing a new instance to be created if necessary.
     *
     * @see #getInstance()
     */
    public static void deleteInstance() {
        GameStage.GameStageHolder.INSTANCE.close();
        GameStage.GameStageHolder.INSTANCE = null;
    }
}
