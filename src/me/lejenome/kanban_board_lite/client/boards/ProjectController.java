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
import java.util.ResourceBundle;
import java.util.Vector;

public class ProjectController extends NodeController {

    @FXML
    TilePane projectsList;
    @FXML
    ScrollPane scrollPane;

    @FXML
    Button usersBtn;

    private Vector<Project> projects;


    public void setProjects(Vector<Project> projects) {
        this.projects = projects;
        refresh(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!RmiClient.account.isAdmin())
            usersBtn.setVisible(false);

        projectsList.prefWidthProperty().bind(scrollPane.widthProperty());
    }

    public void refresh(ActionEvent actionEvent) {
        projectsList.getChildren().clear();
        for (Project p : projects) {
            projectsList.getChildren().add(new ProjectItem(p));
        }
    }

    public void users(ActionEvent actionEvent) {
        Stage stage = new Stage();
        UsersController ctrl = (UsersController) App.Load("boards/users.fxml", stage);
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
