package me.lejenome.kanban_board_lite.client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        TextField email = new TextField();
        email.setPromptText("example@example.com");
        TextField password = new TextField();
        password.setPromptText("Password...");
        Label emailL = new Label("Email");
        Label passwordL = new Label("Password");
        Button registerBtn = new Button("Register");
        registerBtn.setTooltip(new Tooltip("Create an Account"));
        Button loginBtn = new Button("Login");
        loginBtn.setDefaultButton(true);
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setCancelButton(true);


        loginBtn.setOnAction(event -> {
            System.out.println("Login!");
            Stage s = new Stage();
            Group grp = new Group();
            s.setScene(new Scene(grp));
            grp.getChildren().add(new Text("Hello Wrold!"));
            s.show();
        });
        cancelBtn.setOnAction(e -> {
            stage.close();
        });


        GridPane grid = new GridPane();
        grid.add(emailL, 0, 0);
        grid.add(email, 1, 0, 4, 1);
        grid.add(passwordL, 0, 1);
        grid.add(password, 1, 1, 4, 1);
        grid.add(registerBtn, 0, 2);
        grid.add(cancelBtn, 3, 2);
        grid.add(loginBtn, 4, 2);
        grid.setHgap(20);
        grid.setVgap(5);


        Group g = new Group();
        Scene sc = new Scene(g, 400, 400);
        g.getChildren().add(grid);
        stage.setScene(sc);
        stage.setTitle("Login - Kanban Board Lite");
        stage.setOnCloseRequest(e -> stage.close());
        Pagination p = new Pagination(3);
        p.setPageFactory(param -> {
            return grid;
        });
        stage.show();
        stage.setMinHeight(115);
        stage.setMinWidth(275);
        stage.sizeToScene();
    }
}