package me.lejenome.kanban_board_lite.client.authentication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import me.lejenome.kanban_board_lite.client.App;
import me.lejenome.kanban_board_lite.client.RmiClient;
import me.lejenome.kanban_board_lite.common.Account;
import me.lejenome.kanban_board_lite.common.AccountNotFoundException;
import me.lejenome.kanban_board_lite.common.AuthenticationException;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController extends AnchorPane implements Initializable {

    @FXML
    TextField email;

    @FXML
    PasswordField password;

    @FXML
    Label errorMessage;

    public void login(ActionEvent actionEvent) {
        try {
            errorMessage.setText("");
            Account acc = RmiClient.kanbanManager.authenticate(email.getText(), password.getText());
            System.out.println("Register: " + acc);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            errorMessage.setText("Account Not Found");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
            errorMessage.setText("Authentication Failure");
        }
    }

    public void cancel(ActionEvent actionEvent) {
        App.stage.close();
    }

    public void change_register(ActionEvent actionEvent) {
        App.stage.setScene(App.registerScene);
        App.stage.sizeToScene();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
