package me.lejenome.kanban_board_lite.client.boards;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import me.lejenome.kanban_board_lite.client.NodeController;
import me.lejenome.kanban_board_lite.client.RmiClient;
import me.lejenome.kanban_board_lite.common.Project;
import me.lejenome.kanban_board_lite.common.Ticket;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Created by lejenome on 12/8/16.
 */
public class TicketController extends NodeController {

    @FXML
    HBox ticketsBox;
    @FXML
    ListView<Ticket> backlogTickets;
    @FXML
    ListView<Ticket> readyTickets;
    @FXML
    ListView<Ticket> inProgressTickets;
    @FXML
    ListView<Ticket> doneTickets;
    ListView<Ticket> focused = backlogTickets;
    private Vector<Ticket> tickets;
    private Project project;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backlogTickets.focusedProperty().addListener((e, a, b) -> {
            if (e.getValue()) focused = backlogTickets;
        });
        readyTickets.focusedProperty().addListener((e, a, b) -> {
            if (e.getValue()) focused = readyTickets;
        });
        inProgressTickets.focusedProperty().addListener((e, a, b) -> {
            if (e.getValue()) focused = inProgressTickets;
        });
        doneTickets.focusedProperty().addListener((e, a, b) -> {
            if (e.getValue()) focused = doneTickets;
        });
    }


    public void edit(ActionEvent actionEvent) {
        Stage stage = new Stage();
        TicketEditController ctrl = (TicketEditController) app.load("boards/ticket.fxml", stage);
        ctrl.setTicket(focused.getSelectionModel().getSelectedItem());
        ctrl.setProject(project);
        stage.show();
    }

    public void setProject(Project p) {
        this.project = p;
        refresh(null);
    }

    public void add(ActionEvent actionEvent) {
        Stage stage = new Stage();
        TicketEditController ctrl = (TicketEditController) app.load("boards/ticket.fxml", stage);
        ;
        ctrl.setProject(project);
        ctrl.setStage(stage);
        ctrl.setProjectBoard(this);
        stage.show();
    }

    public void refresh(ActionEvent actionEvent) {
        try {
            this.tickets = RmiClient.kanbanManager.listTickets(project);
            backlogTickets.setItems(FXCollections.observableArrayList());
            readyTickets.setItems(FXCollections.observableArrayList());
            inProgressTickets.setItems(FXCollections.observableArrayList());
            doneTickets.setItems(FXCollections.observableArrayList());
            for (Ticket t : tickets) {
                switch (t.getStatus()) {
                    case 0:
                        backlogTickets.getItems().add(t);
                        break;
                    case 3:
                        readyTickets.getItems().add(t);
                        break;
                    case 6:
                        inProgressTickets.getItems().add(t);
                        break;
                    case 9:
                        doneTickets.getItems().add(t);
                        break;
                }

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
