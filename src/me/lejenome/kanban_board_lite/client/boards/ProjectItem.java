package me.lejenome.kanban_board_lite.client.boards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import me.lejenome.kanban_board_lite.client.App;
import me.lejenome.kanban_board_lite.client.RmiClient;
import me.lejenome.kanban_board_lite.common.Project;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;

public class ProjectItem extends AnchorPane {
    @FXML
    Label name;
    @FXML
    Label description;
    @FXML
    Label numBacklog;
    @FXML
    Label numReady;
    @FXML
    Label numInProgress;
    @FXML
    Label numDone;
    @FXML
    Button editBtn;
    private Project project;

    public ProjectItem(Project project) {
        this();
        setProject(project);
    }

    public ProjectItem() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("projectItem.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        name.setText(project.getName());
        description.setText(project.getDescription());
        numBacklog.setText("0");
        numReady.setText("0");
        numInProgress.setText("0");
        numDone.setText("0");
        if (!RmiClient.account.isAdmin())
            editBtn.setVisible(false);
        try {
            HashMap<Integer, Integer> chart = RmiClient.kanbanManager.ticketChart(project);
            numBacklog.setText(chart.getOrDefault(0, 0).toString());
            numReady.setText(chart.getOrDefault(3, 0).toString());
            numInProgress.setText(chart.getOrDefault(6, 0).toString());
            numDone.setText(chart.getOrDefault(9, 0).toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void edit(ActionEvent e) {
        Stage stage = new Stage();
        ProjectEditController ctrl = (ProjectEditController) App.Load("boards/projectEdit.fxml", stage);
        ctrl.setProject(project);
        stage.show();
    }

    @FXML
    public void view(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Stage stage = new Stage();
            TicketController ctrl = (TicketController) App.Load("boards/tickets.fxml", stage);
            ctrl.setProject(project);
            stage.show();
        }
    }
}
