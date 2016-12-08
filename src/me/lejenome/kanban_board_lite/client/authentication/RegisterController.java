package me.lejenome.kanban_board_lite.client.authentication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import me.lejenome.kanban_board_lite.client.NodeController;
import me.lejenome.kanban_board_lite.client.RmiClient;
import me.lejenome.kanban_board_lite.common.Account;
import me.lejenome.kanban_board_lite.common.AccountExistsException;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class RegisterController extends NodeController {

    @FXML
    TextField firstName;

    @FXML
    TextField lastName;

    @FXML
    TextField email;

    @FXML
    PasswordField password;

    @FXML
    Label errorMessage;

    public void register(ActionEvent actionEvent) {
        try {
            errorMessage.setText("");
            Account acc = RmiClient.kanbanManager.register(firstName.getText(), lastName.getText(), email.getText(), password.getText());
            app.load("boards/projects.fxml");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AccountExistsException e) {
            e.printStackTrace();
            errorMessage.setText("Account Exists");
        }
    }

    public void cancel(ActionEvent actionEvent) {
        app.close();
    }

    public void change_login(ActionEvent actionEvent) {
        app.load("authentication/login.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
