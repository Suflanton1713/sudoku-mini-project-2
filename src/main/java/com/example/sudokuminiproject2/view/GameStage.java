package com.example.sudokuminiproject2.view;

import com.example.sudokuminiproject2.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GameStage extends Stage {
    private GameController gameController;

    public GameController getGameController() {
        return gameController;
    }

    public GameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudokuminiproject2/game-view.fxml"));
        Parent root = loader.load();

        gameController = loader.getController();

        setMaximized(true);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/sudokuminiproject2/styleGame.css").toExternalForm());
        setScene(scene);
        setTitle("Sudoku");
        getIcons().add(new Image(getClass().getResourceAsStream("/com/example/sudokuminiproject2/favicon.png")));
        setResizable(true);

        show();
    }

    private static class GameStageHolder{
        private static GameStage INSTANCE;
    }

    public static GameStage getInstance() throws IOException{
        GameStage.GameStageHolder.INSTANCE =
                GameStage.GameStageHolder.INSTANCE != null ?
                        GameStage.GameStageHolder.INSTANCE : new GameStage();
        return GameStage.GameStageHolder.INSTANCE;
    }

    public static void deleteInstance(){
        GameStage.GameStageHolder.INSTANCE.close();
        GameStage.GameStageHolder.INSTANCE = null;
    }
}


