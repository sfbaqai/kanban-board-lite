package me.lejenome.kanban_board_lite.client.boards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import me.lejenome.kanban_board_lite.client.NodeController;
import me.lejenome.kanban_board_lite.client.RmiClient;
import me.lejenome.kanban_board_lite.common.Account;
import me.lejenome.kanban_board_lite.common.AccountExistsException;
import me.lejenome.kanban_board_lite.common.AccountNotFoundException;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by lejenome on 12/12/16.
 */
public class UserEditController extends NodeController {
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField email;
    @FXML
    ComboBox<String> role;

    private Account account;
    private UsersController usersController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!RmiClient.account.isAdmin()) {
            role.setDisable(true);
        }
        role.getItems().addAll("admin", "user");
    }

    public void setAccount(Account account) {
        this.account = account;
        firstName.setText(account.getFirstName());
        lastName.setText(account.getLastName());
        email.setText(account.getEmail());
        role.getSelectionModel().select(account.getRole());

    }

    public void save(ActionEvent actionEvent) {
        account.setFirstName(firstName.getText());
        account.setLastName(lastName.getText());
        if (RmiClient.account.isAdmin())
            account.setRole(role.getSelectionModel().getSelectedItem());

        try {
            RmiClient.kanbanManager.updateAccount(account);
            if (usersController != null)
                usersController.refresh();
            stage.close();
        } catch (RemoteException | AccountExistsException e) {
            e.printStackTrace();
        }

    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }

    public void setUsersController(UsersController ctrl) {
        this.usersController = ctrl;
    }

    public void delete(ActionEvent actionEvent) {
        try {
            RmiClient.kanbanManager.removeAccount(account);
            if (usersController != null)
                usersController.refresh();
            if (account.getId() == RmiClient.account.getId())
                System.exit(0);
            stage.close();
        } catch (RemoteException | AccountNotFoundException e) {
            e.printStackTrace();
        }
    }
}
