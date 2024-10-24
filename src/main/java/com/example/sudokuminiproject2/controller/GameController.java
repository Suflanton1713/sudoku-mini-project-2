package com.example.sudokuminiproject2.controller;

import com.example.sudokuminiproject2.model.board.Board;
import com.example.sudokuminiproject2.model.board.GameBoard;
import com.example.sudokuminiproject2.model.input.Input;
import com.example.sudokuminiproject2.view.GameStage;
import com.example.sudokuminiproject2.view.WelcomeStage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    private GridPane backgroundPane;


    @FXML
    private Label hintsLabel;

    @FXML
    private Button eraseButton;

    @FXML
    private Label eraseLabel;

    @FXML
    private ImageView imageBackground;

    @FXML
    private javafx.scene.control.Button hintButton;

    @FXML
    private javafx.scene.control.Button undoButton;

    @FXML
    private javafx.scene.control.Button newGameButton;

    @FXML
    private javafx.scene.control.Button draftButton;

    @FXML
    private Label timeLabel;

    @FXML
    private javafx.scene.control.Button pauseButton;

    @FXML
    private Label draftLabel;

    private int segundos = 0;

    private int minutos = 0;

    private Timeline timeline;

    private boolean gamePaused = false;

    private int gameMode;
    private Input input;
    private Board actualBoard;
    private GameBoard gameBoard;
    private boolean isEraseModeOn = false;
    private GameBoard.Hint hints;
    private List<List<TextField>> textFieldBoard = new ArrayList<>(6);
    private List<TextField> textFields = new ArrayList<>();

    public void initialize() {
        eraseLabel.getStyleClass().add("labelOff");
        pauseButton.getStyleClass().add("continueTime");
        draftLabel.getStyleClass().add("labelOff");
        draftLabel.setText("off");
        eraseLabel.setText("off");
        this.input = new Input();
        this.actualBoard = new Board();
        this.gameBoard = new GameBoard(this.actualBoard);
        this.hints = gameBoard.new Hint();
        this.gameMode = 0;
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

        // Creates a timeline that is executed verey second
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> actualizarTiempo()));
        timeline.setCycleCount(Timeline.INDEFINITE);  // Se ejecuta indefinidamente

        timeline.play();
    }

    private void actualizarTiempo() {
        segundos++;
        if (segundos == 60) {
            segundos = 0;
            minutos++;
        }
        // Actualizar el texto del Label
        timeLabel.setText(String.format("%02d:%02d", minutos, segundos));
    }

    @FXML
    void handlePauseButton() {
        if (!gamePaused) {
            pauseButton.getStyleClass().remove("continueButton");
            pauseButton.getStyleClass().add("stopTime");
            timeline.pause(); // Pausar el cronómetro
            for (List<TextField> txtFieldRows : textFieldBoard) {
                for (TextField txtField : txtFieldRows) {
                    txtField.setDisable(true);
                }
            }
            newGameButton.setDisable(true);
            undoButton.setDisable(true);
            hintButton.setDisable(true);
            eraseButton.setDisable(true);
            eraseLabel.setDisable(true);
            draftButton.setDisable(true);
            draftLabel.setDisable(true);
        } else {
            pauseButton.getStyleClass().remove("stopTime");
            pauseButton.getStyleClass().add("continueTime");
            timeline.play(); // Reanudar el cronómetro
            for (List<TextField> txtFieldRows : textFieldBoard) {
                for (TextField txtField : txtFieldRows) {
                    txtField.setDisable(false);
                }
            }
            newGameButton.setDisable(false);
            undoButton.setDisable(false);
            hintButton.setDisable(false);
            eraseButton.setDisable(false);
            eraseLabel.setDisable(false);
            draftButton.setDisable(false);
            draftLabel.setDisable(false);
        }
        gamePaused = !gamePaused; // Alternar estado
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
                index++;
            }
        }
    }

    private void handleTextFields(TextField txt, int row, int col) {
        txt.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!txt.isEditable()) {
                    return;
                }
                String currentText = txt.getText();
                if (input.isValidLength(currentText) && input.isValidNumber(currentText)) {
                    int number = Integer.parseInt(currentText);
                    if(gameMode==0){
                        gameBoard.pushToStack(gameBoard.getNumberByIndex(col,row),row,col);
                        gameBoard.setNumberByIndex(gameBoard.getGameBoard(),number, col, row);
                        validCorrectNumber(number,row,col);
                    }

                } else {
                    txt.clear();
                }
                if (txt.getText().equals("")){
                    txt.getStyleClass().add("default");
                    if(gameMode==0){
                        gameBoard.pushToStack(gameBoard.getNumberByIndex(col,row),row,col);
                        gameBoard.setNumberByIndex(gameBoard.getGameBoard(),0, col, row);
                        gameBoard.setMistakesFix(col,row);
                    }
                    fixIncorrects();
                    clearIncorrectNumbersHighlight(row,col);
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
                System.out.println(txt.getText());
                txt.setStyle("-fx-background-color: lightblue;");

                highlightRowAndColumn(row, col);
                highlightBox(row, col, 0);

                if(!currentText.isEmpty()){
                    int number = Integer.parseInt(currentText);
                    highlightSameNumbers(number);
                }

                if (isEraseModeOn && !(gameBoard.isActualPositionHint(col,row))){
                    System.out.println("Borrar numero "+ gameBoard.getNumberByIndex(col,row) + " fila " + row + " columna " +col);
                        txt.clear();
                        if(!(gameBoard.getNumberByIndex(col,row)==0)){
                            gameBoard.pushToStack(gameBoard.getNumberByIndex(col,row), row, col);
                        }
                        gameBoard.setNumberByIndex(gameBoard.getGameBoard(), 0, col, row);
                        gameBoard.setMistakesFix(col, row);
                        clearIncorrectNumbersHighlight(row, col);
                        txt.getStyleClass().removeAll("incorrect", "correct");
                        txt.getStyleClass().add("default");
                        txt.setEditable(false);
                        fixIncorrects();
                        System.out.println(gameBoard.showBoard());
                        System.out.println(gameBoard.showMistakesBoard());
                        System.out.println("initial hints board: ");
                        System.out.println(gameBoard.showInitialHintsBoard());


                }else if (!isEraseModeOn ) {
                    if (gameBoard.getInitialHintsBoard().get(col).get(row) == 0) {
                        txt.setEditable(true);
                    }
                }
            }
        });
    }
    public void validCorrectNumber(int number, int row, int col){
        if((gameBoard.isNumberByColumnAllowed(gameBoard.getGameBoard(),number, col,row))&&(gameBoard.isNumberByRowAllowed(gameBoard.getGameBoard(),number,col,row))&&(gameBoard.isNumberByBoxAllowed(gameBoard.getGameBoard(),number,col,row))){
            textFieldBoard.get(row).get(col).getStyleClass().removeAll("incorrect", "default");
            textFieldBoard.get(row).get(col).getStyleClass().add("correct");
            gameBoard.setMistakesFix(col,row);
        }else{
            textFieldBoard.get(row).get(col).getStyleClass().removeAll("correct", "default");
            textFieldBoard.get(row).get(col).getStyleClass().add("incorrect");
            highlightIncorrectNumbers(number,row,col);
            highlightBox(row,col,number);
            gameBoard.setMistakes(col,row);
        }
    }

    public void fixIncorrects(){
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                if(gameBoard.getMistakesBoard().get(i).get(j)==1){
                    int number = gameBoard.getNumberByIndex(i,j);
                    validCorrectNumber(number,j,i);
                    clearIncorrectNumbersHighlight(j,i);
                }
            }
        }
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
    public void clearIncorrectNumbersHighlight(int row, int col){
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (i != row && j != col ) {
                    textFieldBoard.get(i).get(j).setStyle("");
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            if (i != col ) {
                textFieldBoard.get(row).get(i).setStyle("");
            }
        }
        //quita number incorrect highlight col
        for (int i = 0; i < 6; i++) {
            if (i != row) {
                textFieldBoard.get(i).get(col).setStyle("");
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
        for (int i=0; i<6; i++){
            for (int j=0; j<6; j++){
                if (gameBoard.getNumberByIndex(i,j)>=1 && gameBoard.getNumberByIndex(i,j)<=6){
                    String initialBoardNumber =String.valueOf(gameBoard.getNumberByIndex(i,j));
                    gameBoard.setNumberByIndex(gameBoard.getInitialHintsBoard(),gameBoard.getNumberByIndex(i,j),i,j);
                    textFieldBoard.get(j).get(i).setText(initialBoardNumber);
                    textFieldBoard.get(j).get(i).setEditable(false);
                    System.out.println("initial hints board: ");
                    System.out.println(gameBoard.showInitialHintsBoard());
                }
            }
        }
    }
    public void win(){
        if(gameBoard.isWinner()){
           handlePauseButton();
            for (int i=0; i<6; i++){
                for (int j=0; j<6; j++){
                    textFieldBoard.get(j).get(i).setEditable(false);
                }
            }
            pauseButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("GANASTE ¡FELICITACIONES!");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText(null);
            DialogPane dialogPane = alert.getDialogPane();
            ButtonType okButtonType = ButtonType.OK;
            Button okButton = (Button) alert.getDialogPane().lookupButton(okButtonType);
            Label resultsLabel = new Label("Tiempo en el que lo solucionaste: " + timeLabel.getText());

            okButton.setStyle(
                    "-fx-background-color: purple; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 25px;"
            );

            resultsLabel.setStyle("-fx-font-family: 'JetBrains Mono'; " +
                    "-fx-text-fill: black; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-size: 32px;");

            VBox content = new VBox();

            content.getChildren().add(resultsLabel);
            content.setStyle("-fx-alignment: center;");
            VBox.setMargin(resultsLabel, new Insets(500, 0, 0, 0));

            content.setAlignment(Pos.BOTTOM_CENTER); // Esto alinea el contenido en la parte inferior
            alert.getDialogPane().setContent(content);
            dialogPane.getStylesheets().add(getClass().getResource("/com/example/sudokuminiproject2/styleGame.css").toExternalForm());
            dialogPane.getStyleClass().add("mi-alerta");
            alert.show();


            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> {
                agregarPetalo(dialogPane);
            }));

            timeline.setCycleCount(10);
            timeline.play();


        }
    }

    private void agregarPetalo(DialogPane pane) {
        Image petaloImage = new Image(getClass().getResourceAsStream("/com/example/sudokuminiproject2/rosas.png")); // Asegúrate de usar la ruta correcta
        ImageView petaloView = new ImageView(petaloImage);

        petaloView.setFitWidth(20);
        petaloView.setFitHeight(20);

        Random random = new Random();
        double x = random.nextDouble() * (1140 - 20);
        petaloView.setLayoutX(x);
        petaloView.setLayoutY(0);
        pane.getChildren().add(petaloView);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(3), petaloView);
        transition.setFromY(0);
        transition.setToY(500);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setInterpolator(javafx.animation.Interpolator.LINEAR);
        transition.setOnFinished(event -> {
            petaloView.setLayoutY(0);
            petaloView.setLayoutX(random.nextDouble() * (1140 - 20));
        });

        transition.play();
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

    @FXML
    void handleHint(ActionEvent event) {
        if(hints.getHints() != -50){

            int[] hintHandler;
            int[] hintHardHandler;
            hintHandler = hints.randomHint();
            //randomHint busca las posiciones vacías en donde puede ir una pista.
            //Si hay una posición vacía en donde puede ir entonces lo llena con un número del tablero ideal..

            if(hintHandler[0]!=-1){
                //Si no genera error colocarlo en una posición vacía entonces lo coloca en la matriz en la función
                //Y luego lo coloca en el textField
//            System.out.println("Número "+ hintHandler[0] +" Column "+ hintHandler[1] +" Row "+ hintHandler[2]);
                gameBoard.pushToStack(gameBoard.getNumberByIndex(hintHandler[1],hintHandler[2]),hintHandler[2],hintHandler[1]);
                gameBoard.setNumberByIndex(gameBoard.getGameBoard(), hintHandler[0],hintHandler[1],hintHandler[2]);
                textFieldBoard.get(hintHandler[2]).get(hintHandler[1]).setText(String.valueOf(hintHandler[0]));
                textFieldBoard.get(hintHandler[2]).get(hintHandler[1]).getStyleClass().removeAll("incorrect", "default");
                textFieldBoard.get(hintHandler[2]).get(hintHandler[1]).getStyleClass().add("correct");
            }else{
                //Si si genera error colocarlo en la posición vacía, entonces usa la función secondHardTryingHint
                //Que busca por los cuadros en donde hay error, y si un número del 1 al 6 corrige el error, entonces lo coloca ahí
                //Si no hay errores o no se pueden solucionar, entonces empieza a buscar por espacios llenos en los cuales
                //si es posible cambiar por un número del 1 al 6 sin generar error, entonces lo coloca ahí.
                hintHardHandler= hints.secondHardTryingHint();
                if(hintHardHandler[0]!=-1){
                    //Si si encuentra por el segundo método, lo cambia en la matriz del modelo y lo pone el textfield y cambia el estilo, falta el background je
//                System.out.println("Número "+ hintHardHandler[0] +" Column "+ hintHardHandler[1] +" Row "+ hintHardHandler[2]);
                    gameBoard.pushToStack(gameBoard.getNumberByIndex(hintHardHandler[1],hintHardHandler[2]),hintHardHandler[2],hintHardHandler[1]);
                    gameBoard.setNumberByIndex(gameBoard.getGameBoard(), hintHardHandler[0],hintHardHandler[1],hintHardHandler[2]);
                    textFieldBoard.get(hintHardHandler[2]).get(hintHardHandler[1]).setText(String.valueOf(hintHardHandler[0]));
                    textFieldBoard.get(hintHardHandler[2]).get(hintHardHandler[1]).getStyleClass().removeAll("incorrect");
                    textFieldBoard.get(hintHardHandler[2]).get(hintHardHandler[1]).getStyleClass().add("correct");
                }else{
                    //Si no encuentra posición donde colocarlo de ninguna forma, entonces manda un error.
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("NO ES POSIBLE UNA PISTA QUE AYUDE");
                    alert.setContentText("La pista actual generada no ayuda en el tablero actual, intenta" +
                            "cambiar números o hacerlo por tu parte");
                    alert.show();
                }
            }

            win();
            actualBoard.showBoard();
            gameBoard.showMistakesBoard();
            hintsLabel.setText(String.valueOf(hints.getHints()));
            if(hints.getHints()==-50){
                hintButton.setDisable(true);
            }
        }else{
            System.out.println("No hints allowed");
        }



    }

    @FXML
    void handleUndo(ActionEvent event) {
        System.out.println(gameBoard.showStack());
        if(!gameBoard.getStackList().isEmpty()){
            List<Integer> topList = gameBoard.getStackList().pop();
            int number = topList.get(0);
            int row = topList.get(1);
            int col = topList.get(2);
            System.out.println("Undo numero "+ topList.get(0) + " fila " + topList.get(1) + " columna " +topList.get(2));
            if (number==0){
                gameBoard.setNumberByIndex(gameBoard.getGameBoard(),0, col, row);
                textFieldBoard.get(row).get(col).clear();
                textFieldBoard.get(row).get(col).getStyleClass().removeAll("correct", "incorrect");
                textFieldBoard.get(row).get(col).setStyle("");
                gameBoard.setMistakesFix(col,row);
                clearIncorrectNumbersHighlight(row,col);
                System.out.println(gameBoard.showBoard());
            }else{
                gameBoard.setNumberByIndex(gameBoard.getGameBoard(),number, col, row);
                textFieldBoard.get(row).get(col).setText(String.valueOf(number));
                gameBoard.setMistakes(col,row);
                validCorrectNumber(0,row,col);
                clearIncorrectNumbersHighlight(row,col);
                System.out.println(gameBoard.showBoard());
            }
        }else{
            System.out.println("No hay movimientos para deshacer.");
        }
        System.out.println(gameBoard.showStack());
    }


    @FXML
    void onHandleDraft(ActionEvent event) {
        if(gameMode == 1){
            clearHighlights();
            draftLabel.getStyleClass().remove("labelOn");
            draftLabel.getStyleClass().add("labelOff");
            draftLabel.setText("off");
            hintButton.setDisable(false);
            eraseButton.setDisable(false);
            eraseLabel.setDisable(false);
            undoButton.setDisable(false);
            newGameButton.setDisable(false);
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    if(gameBoard.getNumberByIndex(col,row)==0){
                        textFieldBoard.get(row).get(col).clear();
                        textFieldBoard.get(row).get(col).getStyleClass().removeAll("notes");
                        textFieldBoard.get(row).get(col).getStyleClass().removeAll("incorrect");
                        clearIncorrectNumbersHighlight(row, col);

                    }else{
                        textFieldBoard.get(row).get(col).setText(String.valueOf(gameBoard.getNumberByIndex(col,row)));
                        textFieldBoard.get(row).get(col).getStyleClass().removeAll("incorrect");
                        clearIncorrectNumbersHighlight(row, col);
                        if(gameBoard.isActualPositionMistake(col,row)){
                            textFieldBoard.get(row).get(col).getStyleClass().removeAll("notes");
                            textFieldBoard.get(row).get(col).getStyleClass().add("incorrect");
                            textFieldBoard.get(row).get(col).setStyle("-fx-background-color: #ffcccc;");
                        }else{
                            textFieldBoard.get(row).get(col).getStyleClass().removeAll("notes");
                            textFieldBoard.get(row).get(col).getStyleClass().removeAll("hintsButNotes");
                            textFieldBoard.get(row).get(col).getStyleClass().removeAll("incorrect");
                            textFieldBoard.get(row).get(col).getStyleClass().add("default");
                        }

                    }

                }
            }
            gameMode = 0;
        }else{
            draftLabel.getStyleClass().remove("labelOff");
            draftLabel.getStyleClass().add("labelOn");
            draftLabel.setText("on");
            hintButton.setDisable(true);
            eraseButton.setDisable(true);
            undoButton.setDisable(true);
            newGameButton.setDisable(true);
            for (List<TextField> txtFieldRows : textFieldBoard) {
                for (TextField txtField : txtFieldRows) {
                    if(txtField.isEditable()){
                        txtField.getStyleClass().add("notes");
                    }else{

                        txtField.getStyleClass().add("hintsButNotes");
                    }
                }
            }
            System.out.println("Draft mode activated");
            gameMode = 1;
            System.out.println(gameMode + " Mode ");
        }
    }

    @FXML
    void handleErase(ActionEvent event) {
        isEraseModeOn = !isEraseModeOn;
        if (isEraseModeOn) {

            hintButton.setDisable(true);
            undoButton.setDisable(true);
            draftButton.setDisable(true);
            draftLabel.setDisable(true);
            newGameButton.setDisable(true);
            eraseLabel.getStyleClass().remove("labelOff");
            eraseLabel.getStyleClass().add("labelOn");
            eraseLabel.setText("on");
        } else {
            draftLabel.setDisable(false);
            hintButton.setDisable(false);
            undoButton.setDisable(false);
            draftButton.setDisable(false);
            newGameButton.setDisable(false);
            eraseLabel.getStyleClass().remove("labelOn");
            eraseLabel.getStyleClass().add("labelOff");
            eraseLabel.setText("off");
        }
    }
}

