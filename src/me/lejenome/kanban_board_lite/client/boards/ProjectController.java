package me.lejenome.kanban_board_lite.client.boards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import me.lejenome.kanban_board_lite.client.App;
import me.lejenome.kanban_board_lite.client.NodeController;
import me.lejenome.kanban_board_lite.client.RmiClient;
import me.lejenome.kanban_board_lite.common.Project;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.Vector;

public class ProjectController extends NodeController {

    @FXML
    TilePane projectsList;
    @FXML
    ScrollPane scrollPane;

    @FXML
    Button usersBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!RmiClient.account.isAdmin())
            usersBtn.setVisible(false);

        projectsList.prefWidthProperty().bind(scrollPane.widthProperty());
        refresh(null);
    }

    public void refresh(ActionEvent actionEvent) {
        try {
            projectsList.getChildren().clear();
            Vector<Project> projects = RmiClient.kanbanManager.listProjects();
            for (Project p : projects) {
                projectsList.getChildren().add(new ProjectItem(p));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void users(ActionEvent actionEvent) {
        Stage stage = new Stage();
        UsersController ctrl = (UsersController) App.Load("boards/users.fxml", stage);
        stage.show();
    }

    public void account(ActionEvent actionEvent) {
        Stage stage = new Stage();
        UserEditController ctrl = (UserEditController) App.Load("boards/userEdit.fxml", stage);
        ctrl.setAccount(RmiClient.account);
        stage.show();
    }

    class ListCellRenderer extends ListCell<Project> {

        private final static int colorTo = 100;

        @Override
        protected void updateItem(Project project, boolean empty) {
            super.updateItem(project, empty);
            if (project != null) setText(project.getName());
        }
    }
}
