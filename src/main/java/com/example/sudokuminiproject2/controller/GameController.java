package com.example.sudokuminiproject2.controller;

import com.example.sudokuminiproject2.model.board.Board;
import com.example.sudokuminiproject2.model.board.GameBoard;
import com.example.sudokuminiproject2.model.input.Input;
import com.example.sudokuminiproject2.view.GameStage;
import com.example.sudokuminiproject2.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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

    @FXML
    private Label hintsLabel;

    private Input input;
    private Board actualBoard;
    private GameBoard gameBoard;
    private GameBoard.Hint hints;
    private List<List<TextField>> textFieldBoard = new ArrayList<>(6);
    private List<TextField> textFields = new ArrayList<>();

    public void initialize() {
        this.input = new Input();
        this.actualBoard = new Board();
        this.gameBoard = new GameBoard(this.actualBoard);
        this.hints = gameBoard.new Hint();
        initializeTextFieldBoard();
        initializeTextFields();
        assignTextFieldsToBoard();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField currentField = textFieldBoard.get(i).get(j);
                handleTextFields(currentField,i,j);
            }
        }
        showGameBoard();
        System.out.println(actualBoard.showIdealGame());
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
                textFieldBoard.get(i).get(j).getStyleClass().add("default");
                System.out.println("fila" + i + "columna" + j + " essss: " + textFields.get(index));
                index++;
            }
        }
    }

    private void handleTextFields(TextField txt, int row, int col) {
        txt.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String currentText = txt.getText();
                if (input.isValidLength(currentText) && input.isValidNumber(currentText)) {
                    int number = Integer.parseInt(currentText);
                    gameBoard.setNumberByIndex(number, col, row);
                    System.out.println(gameBoard.showBoard());
                    if((gameBoard.isNumberByColumnAllowed(gameBoard.getGameBoard(),number, col,row))&&(gameBoard.isNumberByRowAllowed(gameBoard.getGameBoard(),number,col,row))&&(gameBoard.isNumberByBoxAllowed(gameBoard.getGameBoard(),number,col,row))){
                        txt.getStyleClass().removeAll("incorrect", "default");
                        txt.getStyleClass().add("correct");
                        gameBoard.setMistakesFix(col,row);
                    }else{
                        txt.getStyleClass().removeAll("correct", "default");
                        txt.getStyleClass().add("incorrect");
                        highlightIncorrectNumbers(number,row,col);
                        highlightBox(row,col,number);
                        gameBoard.setMistakes(col,row);
                    }
                } else {
                    txt.setText("");
                }
                if (txt.getText()==""){
                    txt.getStyleClass().add("default");
                    gameBoard.setNumberByIndex(0, col, row);
                    System.out.println(gameBoard.showBoard());
                }
                System.out.println("Numero de errores: "+gameBoard.getMistakeCount());
                win();

            }
        });
        txt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearHighlights();
                String currentText = txt.getText();
                txt.setStyle("-fx-background-color: lightblue;");

                highlightRowAndColumn(row, col);
                highlightBox(row, col, 0);

                if(!currentText.isEmpty()){
                    int number = Integer.parseInt(currentText);
                    highlightSameNumbers(number);
                }
            }
        });
    }
    private void highlightRowAndColumn(int row, int col) {
        // highlight row
        for (int i = 0; i < 6; i++) {
            TextField currentField = textFieldBoard.get(row).get(i);
            if (!currentField.getStyleClass().contains("incorrect")) {
                currentField.getStyleClass().removeAll("default");
                currentField.getStyleClass().add("highlight");
            }

        }
        // highlight col
        for (int i = 0; i < 6; i++) {
            TextField currentField = textFieldBoard.get(i).get(col);
            if (!currentField.getStyleClass().contains("incorrect")) {
                currentField.getStyleClass().removeAll("default");
                currentField.getStyleClass().add("highlight");
            }
        }
    }
    private void highlightBox(int row, int col, int number) {
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;

        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                TextField currentField = textFieldBoard.get(i).get(j);
                //entra aqui para resaltar normalito no errores
                if(number==0){
                    if (!currentField.getStyleClass().contains("incorrect")) {
                        currentField.getStyleClass().removeAll("default");
                        currentField.getStyleClass().add("highlight");
                    }
                }
                //entra aqui para resaltar errores
                if (i != row && j != col && textFieldBoard.get(i).get(j).getText().equals(String.valueOf(number))) {
                    textFieldBoard.get(i).get(j).setStyle("-fx-background-color: #ffcccc;");
                }
            }
        }
    }
    private void highlightSameNumbers(int number) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                String text = textFieldBoard.get(i).get(j).getText();
                if (!text.isEmpty() && Integer.parseInt(text) == number) {
                    textFieldBoard.get(i).get(j).getStyleClass().add("highlight");
                }
            }
        }
    }
    public void highlightIncorrectNumbers(int number, int row, int col) {
        // Revisa y resalta la fila
        for (int i = 0; i < 6; i++) {
            if (i != col && textFieldBoard.get(row).get(i).getText().equals(String.valueOf(number))) {
                textFieldBoard.get(row).get(i).setStyle("-fx-background-color: #ffcccc;");
            }
        }
        // Revisa y resalta la columna
        for (int i = 0; i < 6; i++) {
            if (i != row && textFieldBoard.get(i).get(col).getText().equals(String.valueOf(number))) {
                textFieldBoard.get(i).get(col).setStyle("-fx-background-color: #ffcccc;");
            }
        }

    }

    private void clearHighlights() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField currentField = textFieldBoard.get(i).get(j);
                currentField.getStyleClass().removeAll("highlight");
                currentField.setStyle("");
                if (!currentField.getStyleClass().contains("correct") && !currentField.getStyleClass().contains("incorrect")) {
                    currentField.getStyleClass().add("default");
                }
            }
        }
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
    public void win(){
        if(gameBoard.isWinner()){
            System.out.println("GANASTEEE YEIIPISSSSSSS");
            for (int i=0; i<6; i++){
                for (int j=0; j<6; j++){
                    textFieldBoard.get(j).get(i).setEditable(false);
                }
            }
        }
    }

    public boolean isCorrectNumberIdeal(TextField currentField, int row, int col) {
        int number = Integer.parseInt(currentField.getText());
        if (actualBoard.getNumberByIndex(col,row) == number) {
            return true;
        }else{
            return false;
        }
    }

    @FXML
    void handleReturn(ActionEvent event) throws IOException {
        WelcomeStage.getInstance();
        GameStage.deleteInstance();
    }
    @FXML
    void handleNewGame(ActionEvent event) throws IOException {
        gameBoard.restartBoardForNewGame();
        restartTextFieldBoard();
        showGameBoard();
    }

    public void restartTextFieldBoard(){
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                textFieldBoard.get(j).get(i).setText("");
                textFieldBoard.get(j).get(i).setEditable(true);
                textFieldBoard.get(j).get(i).getStyleClass().removeAll("correct", "incorrect");
                textFieldBoard.get(j).get(i).getStyleClass().add("default");
            }
        }
    }
}

