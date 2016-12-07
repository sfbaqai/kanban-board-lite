package me.lejenome.kanban_board_lite.client.authentication;/**
 * Created by lejenome on 12/6/16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("login.fxml"));
            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
