package com.example.sudokuminiproject2.controller;

import com.example.sudokuminiproject2.view.GameStage;
import com.example.sudokuminiproject2.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * The WelcomeController class handles the user interactions on the welcome screen of the Sudoku game.
 * It is responsible for managing the transition from the welcome screen to the game stage.
 *
 * @author Maria Juliana Saavedra
 * @author Libardo Alejandro Quintero
 * @version 1.0
 * @see GameStage
 * @see WelcomeStage
 */
public class WelcomeController {

    /**
     * Handles the action when the user presses the "Play" button.
     * This method initializes the game stage and deletes the welcome stage.
     *
     * @param event The event triggered by the user's action.
     * @throws IOException If an I/O error occurs during the stage transition.
     * @see GameStage#getInstance()
     * @see WelcomeStage#deleteInstance()
     */
    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        GameStage.getInstance();
        WelcomeStage.deleteInstance();
    }
}
