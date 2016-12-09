package me.lejenome.kanban_board_lite.client.authentication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import me.lejenome.kanban_board_lite.client.NodeController;
import me.lejenome.kanban_board_lite.client.RmiClient;
import me.lejenome.kanban_board_lite.client.boards.ProjectController;
import me.lejenome.kanban_board_lite.common.AccountNotFoundException;
import me.lejenome.kanban_board_lite.common.AuthenticationException;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController extends NodeController {

    @FXML
    TextField email;

    @FXML
    PasswordField password;

    @FXML
    Label errorMessage;

    public void login(ActionEvent actionEvent) {
        try {
            errorMessage.setText("");
            RmiClient.account = RmiClient.kanbanManager.authenticate(email.getText(), password.getText());
            ProjectController ctrl = (ProjectController) app.load("boards/projects.fxml");
            ctrl.setProjects(RmiClient.kanbanManager.listProjects());
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
        app.close();
    }

    public void change_register(ActionEvent actionEvent) {
        app.load("authentication/register.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
