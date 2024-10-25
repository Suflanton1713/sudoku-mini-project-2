package com.example.sudokuminiproject2.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the welcome stage of the Sudoku game. This class handles the initialization and display
 * of the welcome screen, which is the first stage the user interacts with. It follows the Singleton pattern
 * to ensure that only one instance of the welcome stage is active at a time.
 *
 * @author María Juliana Saavedra
 * @author Libardo Alejandro Quintero
 * @version 1.0
 * @see GameStage
 */
public class WelcomeStage extends Stage {

    /**
     * Constructs the welcome stage.
     * Loads the FXML layout for the welcome screen, applies stylesheets, sets the window title and icon,
     * and maximizes the window by default.
     *
     * @throws IOException if there is an error in loading the FXML file for the welcome view
     */
    public WelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudokuminiproject2/welcome-view.fxml"));
        Parent root = loader.load();
        setMaximized(true);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/sudokuminiproject2/styleWelcome.css").toExternalForm());
        setScene(scene);
        setTitle("Sudoku");
        getIcons().add(new Image(getClass().getResourceAsStream("/com/example/sudokuminiproject2/favicon.png")));
        setResizable(true);
        show();
    }

    /**
     * Holds the singleton instance of {@code WelcomeStage}.
     * This class follows the Singleton design pattern to ensure that only one instance of
     * the {@code WelcomeStage} exists at any given time.
     */
    private static class WelcomeStageHolder {
        private static WelcomeStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@code WelcomeStage}.
     * If the instance does not already exist, it creates a new one. Otherwise, it returns the existing instance.
     *
     * @return the singleton instance of {@code WelcomeStage}
     * @throws IOException if there is an error in creating the {@code WelcomeStage}
     * @see #deleteInstance()
     */
    public static WelcomeStage getInstance() throws IOException {
        WelcomeStage.WelcomeStageHolder.INSTANCE =
                WelcomeStage.WelcomeStageHolder.INSTANCE != null ?
                        WelcomeStage.WelcomeStageHolder.INSTANCE : new WelcomeStage();
        return WelcomeStage.WelcomeStageHolder.INSTANCE;
    }

    /**
     * Deletes the singleton instance of {@code WelcomeStage}.
     * This method closes the current instance and sets it to null, allowing a new instance to be created
     * if necessary.
     *
     * @see #getInstance()
     */
    public static void deleteInstance() {
        WelcomeStageHolder.INSTANCE.close();
        WelcomeStageHolder.INSTANCE = null;
    }
}
