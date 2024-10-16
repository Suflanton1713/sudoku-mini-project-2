package com.example.sudokuminiproject2.controller;

import com.example.sudokuminiproject2.model.board.Board;
import com.example.sudokuminiproject2.model.board.GameBoard;
import com.example.sudokuminiproject2.model.input.Input;
import com.example.sudokuminiproject2.view.GameStage;
import com.example.sudokuminiproject2.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController {
    @FXML
    private TextField textField00;

    @FXML
    private TextField textField01;

    @FXML
    private TextField textField02;

    @FXML
    private TextField textField03;

    @FXML
    private TextField textField04;

    @FXML
    private TextField textField05;

    @FXML
    private TextField textField10;

    @FXML
    private TextField textField11;

    @FXML
    private TextField textField12;

    @FXML
    private TextField textField13;

    @FXML
    private TextField textField14;

    @FXML
    private TextField textField15;

    @FXML
    private TextField textField20;

    @FXML
    private TextField textField21;

    @FXML
    private TextField textField22;

    @FXML
    private TextField textField23;

    @FXML
    private TextField textField24;

    @FXML
    private TextField textField25;

    @FXML
    private TextField textField30;

    @FXML
    private TextField textField31;

    @FXML
    private TextField textField32;

    @FXML
    private TextField textField33;

    @FXML
    private TextField textField34;

    @FXML
    private TextField textField35;

    @FXML
    private TextField textField40;

    @FXML
    private TextField textField41;

    @FXML
    private TextField textField42;

    @FXML
    private TextField textField43;

    @FXML
    private TextField textField44;

    @FXML
    private TextField textField45;

    @FXML
    private TextField textField50;

    @FXML
    private TextField textField51;

    @FXML
    private TextField textField52;

    @FXML
    private TextField textField53;

    @FXML
    private TextField textField54;

    @FXML
    private TextField textField55;

    private Input input;
    private Board actualBoard;
    private GameBoard gameBoard;
    private List<List<TextField>> textFieldBoard = new ArrayList<>(6);
    private List<TextField> textFields = new ArrayList<>();

    public void initialize() {
        this.input = new Input();
        this.actualBoard = new Board();
        this.gameBoard = new GameBoard(this.actualBoard);
        initializeTextFieldBoard();
        initializeTextFields();
        assignTextFieldsToBoard();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField currentField = textFieldBoard.get(i).get(j);
                handleTextFields(currentField);
            }
        }
        showGameBoard();
    }

    private void initializeTextFieldBoard() {
        for (int i = 0; i < 6; i++) {
            List<TextField> row = new ArrayList<>(6);
            for (int x = 0; x < 6; x++) {
                row.add(null);
            }
            textFieldBoard.add(row);
        }
    }

    private void initializeTextFields() {
        textFields.addAll(Arrays.asList(
                textField00, textField01, textField02, textField03, textField04, textField05,
                textField10, textField11, textField12, textField13, textField14, textField15,
                textField20, textField21, textField22, textField23, textField24, textField25,
                textField30, textField31, textField32, textField33, textField34, textField35,
                textField40, textField41, textField42, textField43, textField44, textField45,
                textField50, textField51, textField52, textField53, textField54, textField55
        ));
    }
    private void assignTextFieldsToBoard() {
        int index = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                textFieldBoard.get(i).set(j, textFields.get(index));
                System.out.println("fila" + i + "columna" + j + " essss: " + textFields.get(index));
                index++;
            }
        }
    }



    private void handleTextFields(TextField txt) {
        txt.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String currentText = txt.getText();
                if (input.isValidLength(currentText) && input.isValidNumber(currentText)) {
                    System.out.println("Entrada válida: " + currentText);
                } else {
                    txt.setText("");
                }

            }
        });
    }

    public void showGameBoard(){
        gameBoard.setInitialHints();
        System.out.println(gameBoard.showBoard());
        for (int i=0; i<6; i++){
            for (int j=0; j<6; j++){
                System.out.println(gameBoard.getNumberByIndex(i,j));
                System.out.println(textFieldBoard.get(j).get(i));
                if (gameBoard.getNumberByIndex(i,j)>=1 && gameBoard.getNumberByIndex(i,j)<=6){
                    String initialBoardNumber =String.valueOf(gameBoard.getNumberByIndex(i,j));
                    textFieldBoard.get(j).get(i).setText(initialBoardNumber);
                    textFieldBoard.get(j).get(i).setEditable(false);
                }
            }
        }

    }

    @FXML
    void handleReturn(ActionEvent event) throws IOException {
        WelcomeStage.getInstance();
        GameStage.deleteInstance();
    }

}
