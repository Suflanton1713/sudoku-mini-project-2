package com.example.sudokuminiproject2;

import com.example.sudokuminiproject2.model.board.Board;
import com.example.sudokuminiproject2.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.management.modelmbean.ModelMBean;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        long startTime = System.currentTimeMillis();
        Board board = new Board();
        System.out.println(board.showBoard());

        long endTime = System.currentTimeMillis(); // Tiempo final
        long duration = endTime - startTime; // Duración en milisegundos

        System.out.println("Tiempo de ejecución: " + duration + " ms");


    }
}
