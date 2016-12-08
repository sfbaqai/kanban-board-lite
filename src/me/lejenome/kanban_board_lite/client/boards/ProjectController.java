package me.lejenome.kanban_board_lite.client.boards;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    }

    public void refresh(ActionEvent actionEvent) {
        projectsList.setItems(FXCollections.observableArrayList(projects));
    }

    public void view(ActionEvent actionEvent) {
        Project p = projectsList.getSelectionModel().getSelectedItem();
        TicketController ctrl = (TicketController) app.load("boards/tickets.fxml");
        ctrl.setProject(p);
    }
}
