package me.lejenome.kanban_board_lite.client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
    // TODO implement SWING/JavaFX interface
    public static Stage stage;
    public static Scene loginScene;
    public static Scene registerScene;


    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        loginScene = new Scene((AnchorPane) FXMLLoader.load(getClass().getResource("authentication/login.fxml")));
        registerScene = new Scene((AnchorPane) FXMLLoader.load(getClass().getResource("authentication/register.fxml")));


        stage.setTitle("Kanban Board Lite");

        stage.setScene(this.loginScene);

        stage.show();

    }

}
