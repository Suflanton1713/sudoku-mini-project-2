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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The {@code GameController} class handles user interactions in the game stage of the game.
 * It manages the input for the game, validates guesses, updates the UI,
 * and controls game logic such as winning and losing conditions.
 * <p>
 * The game board is represented by a 6x6 grid of {@link TextField} elements,
 * which are organized into a 2D list. This controller also handles hints, mistakes,
 * and other game mechanics.
 * </p>
 *
 * @version 1.0
 * @authors Maria Juliana Saavedra Torres - 202344035
 * @authors Libardo Alejandro Quintero Gómez - 202342181
 * @see GameBoard
 * @see Board
 */
public class GameController {

    /**
     * Text fields representing the game board cells
     */
    @FXML
    private TextField textField00, textField01, textField02, textField03, textField04, textField05,
            textField10, textField11, textField12, textField13, textField14, textField15,
            textField20, textField21, textField22, textField23, textField24, textField25,
            textField30, textField31, textField32, textField33, textField34, textField35,
            textField40, textField41, textField42, textField43, textField44, textField45,
            textField50, textField51, textField52, textField53, textField54, textField55;

    /**
     * Label to display hints
     */
    @FXML
    private Label hintsLabel;

    /**
     * Button to erase content from cells
     */
    @FXML
    private Button eraseButton;

    /**
     * Label to indicate erase mode status
     */
    @FXML
    private Label eraseLabel;

    /**
     * Button to request hints
     */
    @FXML
    private Button hintButton;

    /**
     * Button to undo the last action
     */
    @FXML
    private Button undoButton;

    /**
     * Button to start a new game
     */
    @FXML
    private Button newGameButton;

    /**
     * Button to draft input
     */
    @FXML
    private Button draftButton;

    /**
     * Label to display the elapsed time
     */
    @FXML
    private Label timeLabel;

    /**
     * Button to pause the game
     */
    @FXML
    private Button pauseButton;

    /**
     * Label to indicate draft mode status
     */
    @FXML
    private Label draftLabel;

    /**
     * Variables to track the elapsed time in seconds and minutes
     */
    private int segundos = 0;
    private int minutos = 0;

    /**
     * Timeline object to control the periodic execution of actions, such as updating time
     */
    private Timeline timeline;

    /**
     * Flag to indicate if the game is paused
     */
    private boolean gamePaused = false;

    /**
     * The current game mode
     */
    private int gameMode;

    /**
     * Input handler for the game
     */
    private Input input;

    /**
     * The board object representing the actual state of the game
     */
    private Board actualBoard;

    /**
     * GameBoard object representing the game board with logic
     */
    private GameBoard gameBoard;

    /**
     * Flag to indicate if erase mode is active
     */
    private boolean isEraseModeOn = false;

    /**
     * Hint object to provide hints during the game
     */
    private GameBoard.Hint hints;

    /**
     * 2D list to represent the grid of text fields (game board)
     */
    private List<List<TextField>> textFieldBoard = new ArrayList<>(6);

    /**
     * List to store all text fields
     */
    private List<TextField> textFields = new ArrayList<>();

    /**
     * Initializes the game controller by setting up the game board, text fields, and the timer.
     * This method is called automatically after the FXML file has been loaded.
     */
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
                handleTextFields(currentField, i, j);
            }
        }
        showGameBoard();
        // Creates a timeline that is executed every second
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Updates the elapsed time and displays it in the {@code timeLabel}.
     * This method is called every second by the timeline.
     */
    private void updateTime() {
        segundos++;
        if (segundos == 60) {
            segundos = 0;
            minutos++;
        }
        timeLabel.setText(String.format("%02d:%02d", minutos, segundos));
    }

    /**
     * Handles the functionality of the pause button.
     * Pauses the game by stopping the timer and disabling user interaction with the game board.
     */
    @FXML
    void handlePauseButton() {
        if (!gamePaused) {
            pauseButton.getStyleClass().remove("continueButton");
            pauseButton.getStyleClass().add("stopTime");
            timeline.pause();
            disableGameInteraction();
        } else {
            pauseButton.getStyleClass().remove("stopTime");
            pauseButton.getStyleClass().add("continueTime");
            timeline.play();
            enableGameInteraction();
        }
        gamePaused = !gamePaused;
    }

    /**
     * Disables user interaction with the game board and related controls.
     */
    private void disableGameInteraction() {
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
    }

    /**
     * Enables user interaction with the game board and related controls.
     */
    private void enableGameInteraction() {
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

    /**
     * Initializes the board of TextFields (6x6 grid) by creating empty rows and adding them to the {@code textFieldBoard}.
     * Each row is an ArrayList that will hold TextField elements.
     */
    private void initializeTextFieldBoard() {
        for (int i = 0; i < 6; i++) {
            List<TextField> row = new ArrayList<>(6);
            for (int x = 0; x < 6; x++) {
                row.add(null);
            }
            textFieldBoard.add(row);
        }
    }

    /**
     * Initializes the list of TextFields by adding all TextField elements declared in the controller.
     * This method organizes them in a single list for easier access later.
     */
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

    /**
     * Assigns each TextField to its corresponding position in the {@code textFieldBoard}.
     * It also sets the initial CSS style class for each TextField to "default".
     */
    private void assignTextFieldsToBoard() {
        int index = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                textFieldBoard.get(i).set(j, textFields.get(index));  // Assign each TextField to its correct position
                textFieldBoard.get(i).get(j).getStyleClass().add("default");  // Set the default style class
                index++;
            }
        }
    }

    /**
     * Handles the events associated with each TextField, including input validation and mouse interactions.
     * When a key is typed, it checks whether the input is valid and updates the game state.
     * When a TextField is clicked, it highlights the corresponding row, column, and box.
     *
     * @param txt the TextField being handled
     * @param row the row index of the TextField
     * @param col the column index of the TextField
     */
    private void handleTextFields(TextField txt, int row, int col) {
        txt.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!txt.isEditable()) {
                    return;  // If the field is not editable, ignore the input
                }
                String currentText = txt.getText();
                if (input.isValidLength(currentText) && input.isValidNumber(currentText)) {
                    int number = Integer.parseInt(currentText);
                    if (gameMode == 0) {
                        gameBoard.pushToStack(gameBoard.getNumberByIndex(col, row), row, col);
                        gameBoard.setNumberByIndex(gameBoard.getGameBoard(), number, col, row);
                        validCorrectNumber(number, row, col);
                    }
                } else {
                    txt.clear();  // Clear the field if the input is invalid
                }
                if (txt.getText().equals("")) {
                    txt.getStyleClass().add("default");
                    if (gameMode == 0) {
                        gameBoard.pushToStack(gameBoard.getNumberByIndex(col, row), row, col);
                        gameBoard.setNumberByIndex(gameBoard.getGameBoard(), 0, col, row);
                        gameBoard.setMistakesFix(col, row);
                    }
                    fixIncorrects();
                    clearIncorrectNumbersHighlight(row, col);
                }
                win();  // Check if the player has won the game
            }
        });

        txt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearHighlights();  // Clear previous highlights
                String currentText = txt.getText();
                txt.setStyle("-fx-background-color: lightblue;");

                highlightRowAndColumn(row, col);  // Highlight the row and column
                highlightBox(row, col, 0);  // Highlight the 3x3 box

                if (!currentText.isEmpty()) {
                    int number = Integer.parseInt(currentText);
                    highlightSameNumbers(number);  // Highlight other cells with the same number
                }

                if (isEraseModeOn && !(gameBoard.isActualPositionHint(col, row))) {
                    txt.clear();
                    if (!(gameBoard.getNumberByIndex(col, row) == 0)) {
                        gameBoard.pushToStack(gameBoard.getNumberByIndex(col, row), row, col);
                    }
                    gameBoard.setNumberByIndex(gameBoard.getGameBoard(), 0, col, row);
                    gameBoard.setMistakesFix(col, row);
                    clearIncorrectNumbersHighlight(row, col);
                    txt.getStyleClass().removeAll("incorrect", "correct");
                    txt.getStyleClass().add("default");
                    txt.setEditable(false);
                    fixIncorrects();
                } else if (!isEraseModeOn) {
                    if (gameBoard.getInitialHintsBoard().get(col).get(row) == 0) {
                        txt.setEditable(true);  // Allow editing if the cell is not a hint
                    }
                }
            }
        });
    }

    /**
     * Validates whether the number entered in the specified position is correct
     * based on the game rules. If the number is correct, it updates the style to "correct";
     * otherwise, it sets the style to "incorrect".
     *
     * @param number the number entered by the user
     * @param row the row index of the number
     * @param col the column index of the number
     * @see GameBoard#isNumberByColumnAllowed
     * @see GameBoard#isNumberByRowAllowed
     * @see GameBoard#isNumberByBoxAllowed
     */
    public void validCorrectNumber(int number, int row, int col) {
        if ((gameBoard.isNumberByColumnAllowed(gameBoard.getGameBoard(), number, col, row)) &&
                (gameBoard.isNumberByRowAllowed(gameBoard.getGameBoard(), number, col, row)) &&
                (gameBoard.isNumberByBoxAllowed(gameBoard.getGameBoard(), number, col, row))) {
            textFieldBoard.get(row).get(col).getStyleClass().removeAll("incorrect", "default");
            textFieldBoard.get(row).get(col).getStyleClass().add("correct");  // Mark as correct
            gameBoard.setMistakesFix(col, row);
        } else {
            textFieldBoard.get(row).get(col).getStyleClass().removeAll("correct", "default");
            textFieldBoard.get(row).get(col).getStyleClass().add("incorrect");  // Mark as incorrect
            highlightIncorrectNumbers(number, row, col);
            highlightBox(row, col, number);
            gameBoard.setMistakes(col, row);
        }
    }

    /**
     * Fixes the incorrect numbers on the game board by re-validating them.
     * Iterates through the board, and for each cell marked as incorrect in the
     * mistakesBoard, re-validates the number and updates the display accordingly.
     *
     * @see #validCorrectNumber(int, int, int)
     */
    public void fixIncorrects() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (gameBoard.getMistakesBoard().get(i).get(j) == 1) {
                    int number = gameBoard.getNumberByIndex(i, j);
                    validCorrectNumber(number, j, i);
                    clearIncorrectNumbersHighlight(j, i);
                }
            }
        }
    }

    /**
     * Highlights the entire row and column of the specified TextField.
     * Only cells that are not marked as incorrect will be highlighted.
     *
     * @param row the row index of the TextField
     * @param col the column index of the TextField
     */
    private void highlightRowAndColumn(int row, int col) {
        for (int i = 0; i < 6; i++) {
            TextField currentField = textFieldBoard.get(row).get(i);
            if (!currentField.getStyleClass().contains("incorrect")) {
                currentField.getStyleClass().removeAll("default");
                currentField.getStyleClass().add("highlight");
            }
        }

        for (int i = 0; i < 6; i++) {
            TextField currentField = textFieldBoard.get(i).get(col);
            if (!currentField.getStyleClass().contains("incorrect")) {
                currentField.getStyleClass().removeAll("default");
                currentField.getStyleClass().add("highlight");
            }
        }
    }

    /**
     * Highlights the 3x2 box that contains the specified row and column.
     * If the number parameter is 0, highlights only the default cells; otherwise,
     * it highlights cells with the same number as the provided one.
     *
     * @param row the row index of the TextField
     * @param col the column index of the TextField
     * @param number the number to highlight (0 means no specific number)
     */
    private void highlightBox(int row, int col, int number) {
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;

        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                TextField currentField = textFieldBoard.get(i).get(j);

                if (number == 0) {
                    if (!currentField.getStyleClass().contains("incorrect")) {
                        currentField.getStyleClass().removeAll("default");
                        currentField.getStyleClass().add("highlight");
                    }
                }

                if (i != row && j != col && textFieldBoard.get(i).get(j).getText().equals(String.valueOf(number))) {
                    textFieldBoard.get(i).get(j).setStyle("-fx-background-color: #ffcccc;");
                }
            }
        }
    }

    /**
     * Highlights all cells in the board that contain the specified number.
     *
     * @param number the number to highlight
     */
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

    /**
     * Highlights incorrect numbers in the specified row and column, marking them with a red background.
     *
     * @param number the incorrect number to highlight
     * @param row the row index of the cell being validated
     * @param col the column index of the cell being validated
     */
    public void highlightIncorrectNumbers(int number, int row, int col) {
        for (int i = 0; i < 6; i++) {
            if (i != col && textFieldBoard.get(row).get(i).getText().equals(String.valueOf(number))) {
                textFieldBoard.get(row).get(i).setStyle("-fx-background-color: #ffcccc;");
            }
        }

        for (int i = 0; i < 6; i++) {
            if (i != row && textFieldBoard.get(i).get(col).getText().equals(String.valueOf(number))) {
                textFieldBoard.get(i).get(col).setStyle("-fx-background-color: #ffcccc;");
            }
        }
    }

    /**
     * Clears the red background highlight from incorrect cells in the box, row, and column.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     */
    public void clearIncorrectNumbersHighlight(int row, int col) {
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;

        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (i != row && j != col) {
                    textFieldBoard.get(i).get(j).setStyle("");
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            if (i != col) {
                textFieldBoard.get(row).get(i).setStyle("");
            }
        }

        for (int i = 0; i < 6; i++) {
            if (i != row) {
                textFieldBoard.get(i).get(col).setStyle("");
            }
        }
    }

    /**
     * Clears all the current highlights on the game board, resetting each cell's style.
     * Cells that are not marked as correct or incorrect will revert to the default style.
     */
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


    /**
     * Clears all hints from the 6x6 Sudoku board by removing the "initialHints" style class
     * from each {@code TextField} and resetting any inline styles.
     **/
    private void clearInitialHints() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField currentField = textFieldBoard.get(i).get(j);
                currentField.getStyleClass().removeAll("initialHints");
                currentField.setStyle("");
            }
        }
    }

    /**
     * Displays the game board with initial hints. This method sets the initial hints
     * on the board, updates the text fields with the corresponding numbers,
     * and makes the fields uneditable if they contain a valid number between 1 and 6.
     *
     * @see GameBoard#setInitialHints
     */
    public void showGameBoard() {
        gameBoard.setInitialHints();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (gameBoard.getNumberByIndex(i, j) >= 1 && gameBoard.getNumberByIndex(i, j) <= 6) {
                    String initialBoardNumber = String.valueOf(gameBoard.getNumberByIndex(i, j));
                    gameBoard.setNumberByIndex(gameBoard.getInitialHintsBoard(), gameBoard.getNumberByIndex(i, j), i, j);
                    textFieldBoard.get(j).get(i).setText(initialBoardNumber);
                    textFieldBoard.get(j).get(i).setEditable(false);
                    textFieldBoard.get(j).get(i).getStyleClass().add("initialHints");
                    System.out.println("Initial hints board: ");
                    System.out.println(gameBoard.showInitialHintsBoard());
                }
            }
        }
    }

    /**
     * Handles the win condition of the game.
     * If the player has won, the board becomes uneditable, the pause button is disabled, and a congratulatory alert is shown.
     * The alert also displays the time taken to solve the puzzle, and flower petals are animated to fall on the dialog pane.
     *
     * @see GameBoard#isWinner()
     * @see #handlePauseButton()
     */
    public void win() {
        if (gameBoard.isWinner()) {
            gameBoard.setGameOver(true);
            handlePauseButton();
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    textFieldBoard.get(j).get(i).setEditable(false);
                }
            }
            pauseButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("YOU WON! CONGRATULATIONS!");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText(null);
            DialogPane dialogPane = alert.getDialogPane();
            ButtonType okButtonType = ButtonType.OK;
            Button okButton = (Button) alert.getDialogPane().lookupButton(okButtonType);
            Label resultsLabel = new Label("Time taken to solve: " + timeLabel.getText());

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

            content.setAlignment(Pos.BOTTOM_CENTER);
            alert.getDialogPane().setContent(content);
            dialogPane.getStylesheets().add(getClass().getResource("/com/example/sudokuminiproject2/styleGame.css").toExternalForm());
            dialogPane.getStyleClass().add("mi-alerta");
            alert.show();

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> {
                AddPetal(dialogPane);
            }));

            timeline.setCycleCount(10);
            timeline.play();
        }
    }

    /**
     * Adds a petal image to the provided dialog pane.
     * This method randomly positions the petal at the top of the pane and animates it falling downwards.
     * The animation resets after reaching the bottom.
     *
     * @param pane the DialogPane to which the petal will be added
     * @see Timeline
     * @see ImageView
     */
    private void AddPetal(DialogPane pane) {
        Image petaloImage = new Image(getClass().getResourceAsStream("/com/example/sudokuminiproject2/rosas.png"));
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

    /**
     * Returns the user to the welcome screen and deletes the current game instance.
     *
     * @param event the ActionEvent triggered by clicking the return button
     * @throws IOException if there is an error during the screen transition
     * @exception IOException thrown if an I/O error occurs during screen change
     * @see WelcomeStage#getInstance()
     * @see GameStage#deleteInstance()
     */
    @FXML
    void handleReturn(ActionEvent event) throws IOException {
        WelcomeStage.getInstance();
        GameStage.deleteInstance();
    }

    /**
     * Starts a new game by resetting the game board and text fields.
     *
     * @param event the ActionEvent triggered by clicking the new game button
     * @throws IOException if there is an error during the reset process
     * @exception IOException thrown if an I/O error occurs during the reset
     * @see GameBoard#restartBoardForNewGame()
     * @see #restartTextFieldBoard()
     * @see #showGameBoard()
     */
    @FXML
    void handleNewGame(ActionEvent event) throws IOException {
        clearInitialHints();
        gameBoard.restartBoardForNewGame();
        restartTextFieldBoard();
        showGameBoard();
        hints.setHints(5);
        gameBoard.setGameOver(false);
        hintsLabel.setText(String.valueOf(5));
        segundos = 0;
        minutos = 0;
        timeLabel.setText("00:00");

    }

    /**
     * Resets the text fields on the game board.
     * This method clears all text fields, makes them editable, and resets their style to the default.
     *
     * @see #handleNewGame(ActionEvent)
     */
    public void restartTextFieldBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                textFieldBoard.get(j).get(i).setText("");
                textFieldBoard.get(j).get(i).setEditable(true);
                textFieldBoard.get(j).get(i).getStyleClass().removeAll("correct", "incorrect");
                textFieldBoard.get(j).get(i).getStyleClass().add("default");
            }
        }
    }

    /**
     * Handles the hint functionality in the game.
     * This method checks if hints are available and provides a random hint to the player by updating the game board and text fields.
     * If no valid hint is found, it tries a second harder hint. If no helpful hint is available, a warning alert is shown.
     * It also updates the hints label, disables the hint button if no more hints are allowed, and checks for a win condition.
     *
     * @param event the ActionEvent triggered when the hint button is pressed
     * @see //Hints#randomHint()
     * @see //Hints#secondHardTryingHint()
     * @see GameBoard#pushToStack(int, int, int)
     * @see #win()
     */
    @FXML
    void handleHint(ActionEvent event) {
        if (hints.canUseHints()) {
            int[] hintHandler;
            int[] hintHardHandler;
            hintHandler = hints.randomHint();

            if (hintHandler[0] != -1) {
                gameBoard.pushToStack(gameBoard.getNumberByIndex(hintHandler[1], hintHandler[2]), hintHandler[2], hintHandler[1]);
                gameBoard.setNumberByIndex(gameBoard.getGameBoard(), hintHandler[0], hintHandler[1], hintHandler[2]);
                textFieldBoard.get(hintHandler[2]).get(hintHandler[1]).setText(String.valueOf(hintHandler[0]));
                textFieldBoard.get(hintHandler[2]).get(hintHandler[1]).getStyleClass().removeAll("incorrect", "default");
                textFieldBoard.get(hintHandler[2]).get(hintHandler[1]).getStyleClass().add("correct");
            } else {
                hintHardHandler = hints.secondHardTryingHint();
                if (hintHardHandler[0] != -1) {
                    gameBoard.pushToStack(gameBoard.getNumberByIndex(hintHardHandler[1], hintHardHandler[2]), hintHardHandler[2], hintHardHandler[1]);
                    gameBoard.setNumberByIndex(gameBoard.getGameBoard(), hintHardHandler[0], hintHardHandler[1], hintHardHandler[2]);
                    textFieldBoard.get(hintHardHandler[2]).get(hintHardHandler[1]).setText(String.valueOf(hintHardHandler[0]));
                    textFieldBoard.get(hintHardHandler[2]).get(hintHardHandler[1]).getStyleClass().removeAll("incorrect");
                    textFieldBoard.get(hintHardHandler[2]).get(hintHardHandler[1]).getStyleClass().add("correct");
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("NO HELPFUL HINT AVAILABLE");
                    alert.setContentText("The current hint is not helpful for the current board. Try changing numbers or solving it on your own.");
                    alert.show();
                }
            }

            win();
            actualBoard.showBoard();
            gameBoard.showMistakesBoard();
            hintsLabel.setText(String.valueOf(hints.getHints()));
            if (hints.getHints() == -50) {
                hintButton.setDisable(true);
            }
        } else {
            System.out.println("No hints allowed");
        }
    }

    /**
     * Handles the undo functionality.
     * This method undoes the last move made by the player by popping the top entry from the stack,
     * updating the game board and the corresponding text fields, and clearing any incorrect highlights if necessary.
     *
     * @param event the ActionEvent triggered when the undo button is pressed
     * @see GameBoard#getStackList()
     * @see #clearIncorrectNumbersHighlight(int, int)
     */
    @FXML
    void handleUndo(ActionEvent event) {
        System.out.println(gameBoard.showStack());
        clearHighlights();
        if (!gameBoard.getStackList().isEmpty()) {
            List<Integer> topList = gameBoard.getStackList().pop();
            int number = topList.get(0);
            int row = topList.get(1);
            int col = topList.get(2);
            System.out.println("Undo number " + topList.get(0) + " row " + topList.get(1) + " column " + topList.get(2));

            if (number == 0) {
                gameBoard.setNumberByIndex(gameBoard.getGameBoard(), 0, col, row);
                textFieldBoard.get(row).get(col).clear();
                textFieldBoard.get(row).get(col).getStyleClass().removeAll("correct", "incorrect");
                textFieldBoard.get(row).get(col).setStyle("");
                gameBoard.setMistakesFix(col, row);
                highlightRowAndColumn(row, col);
                clearIncorrectNumbersHighlight(row, col);
            } else {
                gameBoard.setNumberByIndex(gameBoard.getGameBoard(), number, col, row);
                textFieldBoard.get(row).get(col).setText(String.valueOf(number));
                gameBoard.setMistakes(col, row);
                highlightRowAndColumn(row, col);
                validCorrectNumber(0, row, col);
                clearIncorrectNumbersHighlight(row, col);
            }
        } else {
            System.out.println("No moves to undo.");
        }
        System.out.println(gameBoard.showStack());
    }

    /**
     * Toggles the draft mode in the game.
     * When the draft mode is active, the player can make temporary notes on the board,
     * which will not affect the final game state. Disables certain controls during this mode.
     * When draft mode is disabled, the game returns to normal mode, and controls are restored.
     *
     * @param event the ActionEvent triggered when the draft mode button is clicked
     * @see #clearHighlights()
     * @see #handleErase(ActionEvent)
     */
    @FXML
    void onHandleDraft(ActionEvent event) {
        if (gameMode == 1) {
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
                    textFieldBoard.get(row).get(col).getStyleClass().removeAll("notes");
                    if (gameBoard.getNumberByIndex(col, row) == 0) {
                        textFieldBoard.get(row).get(col).clear();
                        textFieldBoard.get(row).get(col).getStyleClass().removeAll( "incorrect");
                        clearIncorrectNumbersHighlight(row, col);
                        textFieldBoard.get(row).get(col).getStyleClass().removeAll("notes", "hintsButNotes", "incorrect");
                    } else {
                        textFieldBoard.get(row).get(col).setText(String.valueOf(gameBoard.getNumberByIndex(col, row)));
                        textFieldBoard.get(row).get(col).getStyleClass().removeAll("incorrect");
                        clearIncorrectNumbersHighlight(row, col);
                        if (gameBoard.isActualPositionMistake(col, row)) {
                            textFieldBoard.get(row).get(col).getStyleClass().add("incorrect");
                            textFieldBoard.get(row).get(col).setStyle("-fx-background-color: #ffcccc;");
                        } else {
                            textFieldBoard.get(row).get(col).getStyleClass().removeAll("notes", "hintsButNotes", "incorrect");
                            textFieldBoard.get(row).get(col).getStyleClass().add("default");
                        }
                    }
                }
            }
            gameMode = 0;
        } else {
            draftLabel.getStyleClass().remove("labelOff");
            draftLabel.getStyleClass().add("labelOn");
            draftLabel.setText("on");
            hintButton.setDisable(true);
            eraseButton.setDisable(true);
            undoButton.setDisable(true);
            newGameButton.setDisable(true);


            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    if (gameBoard.isActualPositionHint(col, row)) {
                        textFieldBoard.get(row).get(col).getStyleClass().add("hintsButNotes");
                    } else {
                        textFieldBoard.get(row).get(col).getStyleClass().add("notes");
                    }
                }
                }

            System.out.println("Draft mode activated");
            gameMode = 1;
            System.out.println(gameMode + " Mode ");
        }
    }

    /**
     * Toggles the erase mode in the game.
     * When erase mode is enabled, the player can remove numbers from the board.
     * Certain controls such as hint, undo, and new game buttons are disabled while erase mode is active.
     *
     * @param event the ActionEvent triggered when the erase button is clicked
     * @throws IllegalStateException if the erase mode is incorrectly toggled
     * @exception IllegalStateException if erase mode transition is invalid
     * @see #onHandleDraft(ActionEvent)
     */
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
