package me.lejenome.kanban_board_lite.client.boards;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import me.lejenome.kanban_board_lite.client.App;
import me.lejenome.kanban_board_lite.client.NodeController;
import me.lejenome.kanban_board_lite.client.RmiClient;
import me.lejenome.kanban_board_lite.common.Account;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Created by lejenome on 12/12/16.
 */
public class UsersController extends NodeController {

    @FXML
    TableView<Account> usersTable;

    @FXML
    TableColumn<Account, String> firstNameCol;
    @FXML
    TableColumn<Account, String> lastNameCol;
    @FXML
    TableColumn<Account, String> emailCol;
    @FXML
    TableColumn<Account, String> roleCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        firstNameCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFirstName()));
        lastNameCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastName()));
        emailCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmail()));
        roleCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRole()));
        usersTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                edit();
            }
        });
        refresh();

    }

    private void edit() {
        Account acc = usersTable.getSelectionModel().getSelectedItem();
        if (acc != null) {
            Stage stage = new Stage();
            UserEditController ctrl = (UserEditController) App.Load("boards/userEdit.fxml", stage);
            ctrl.setAccount(acc);
            ctrl.setUsersController(this);
            stage.show();
        }
    }

    public void refresh() {

        try {
            usersTable.getItems().clear();
            Vector<Account> accounts = RmiClient.kanbanManager.listAccounts();
            System.out.println(accounts);
            usersTable.getItems().addAll(accounts);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
