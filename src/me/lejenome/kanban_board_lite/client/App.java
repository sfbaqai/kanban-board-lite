package me.lejenome.kanban_board_lite.client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class App extends Application {
    // TODO implement SWING/JavaFX interface
    private Stage stage;

    public static NodeController Load(String fxml, Stage stage) {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = App.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(App.class.getResource(fxml));
        Pane pane = null;
        try {
            pane = (Pane) loader.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.sizeToScene();
        NodeController controller = (NodeController) loader.getController();
        controller.setStage(stage);
        return controller;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        load("authentication/login.fxml");


        stage.setTitle("Kanban Board Lite");

        stage.show();

    }

    public NodeController load(String fxml) {
        return load(fxml, stage);
    }

    public NodeController load(String fxml, Stage stage) {
        NodeController ctrl = App.Load(fxml, stage);
        ctrl.setApp(this);
        return ctrl;
    }

    public void close() {
        stage.close();
    }
}
