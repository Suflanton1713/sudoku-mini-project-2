package com.example.sudokuminiproject2.controller;
import com.example.sudokuminiproject2.view.GameStage;
import com.example.sudokuminiproject2.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class WelcomeController  {
    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        GameStage.getInstance();
        WelcomeStage.deleteInstance();
    }
}
