package com.example.sudokuminiproject2;

import com.example.sudokuminiproject2.model.board.Board;
import com.example.sudokuminiproject2.model.board.GameBoard;
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

//        GameBoard.Hint hints = gameBoard.new Hint();
//        int[] hintHandler;
//
//        for(int i=0; i<24; i++){
//            if(hints.randomHint()[0]!=-1){
//                hintHandler = hints.randomHint();
//                System.out.println("Número "+ hintHandler[0] +" Column "+ hintHandler[1] +" Row "+ hintHandler[2]);
//            }else{
//                System.out.println("Hint doesnt help the user");
//            }
//
//            System.out.println(i + " hint");
//            System.out.println(gameBoard.showBoard());
//        }
//        System.out.println(hints.getHints());
    }
}
