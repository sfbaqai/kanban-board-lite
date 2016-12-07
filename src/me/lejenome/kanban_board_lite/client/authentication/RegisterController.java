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
import me.lejenome.kanban_board_lite.common.AccountExistsException;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class RegisterController extends AnchorPane implements Initializable {

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
            System.out.println("Register: " + acc);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AccountExistsException e) {
            e.printStackTrace();
            errorMessage.setText("Account Exists");
        }
    }

    public void cancel(ActionEvent actionEvent) {
        App.stage.close();
    }

    public void change_login(ActionEvent actionEvent) {
        App.stage.setScene(App.loginScene);
        App.stage.sizeToScene();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
