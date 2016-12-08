package me.lejenome.kanban_board_lite.client.boards;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import me.lejenome.kanban_board_lite.client.NodeController;
import me.lejenome.kanban_board_lite.common.Project;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class ProjectController extends NodeController {

    @FXML
    ListView<Project> projectsList;
    private Vector<Project> projects;


    public void setProjects(Vector<Project> projects) {
        this.projects = projects;
        refresh(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectsList.setCellFactory(param -> new ListCellRenderer());
    }

    public void refresh(ActionEvent actionEvent) {
        projectsList.setItems(FXCollections.observableArrayList(projects));
    }

    public void view(ActionEvent actionEvent) {
        Project p = projectsList.getSelectionModel().getSelectedItem();
        TicketController ctrl = (TicketController) app.load("boards/tickets.fxml");
        ctrl.setProject(p);
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
