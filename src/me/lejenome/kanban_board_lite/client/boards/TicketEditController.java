package me.lejenome.kanban_board_lite.client.boards;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.lejenome.kanban_board_lite.client.NodeController;
import me.lejenome.kanban_board_lite.client.RmiClient;
import me.lejenome.kanban_board_lite.common.Project;
import me.lejenome.kanban_board_lite.common.Ticket;
import me.lejenome.kanban_board_lite.common.TicketExistsException;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by lejenome on 12/8/16.
 */
public class TicketEditController extends NodeController {

    @FXML
    TextField title;
    @FXML
    TextArea description;
    @FXML
    ComboBox<String> status;
    @FXML
    ComboBox<String> priority;
    @FXML
    DatePicker due;
    private Ticket ticket;
    private Project project;
    private HashMap<Integer, String> TICKET_STATUS;
    private HashMap<Integer, String> TICKET_PRIORITY;

    private Stage stage;
    private TicketController projectBoard;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.TICKET_STATUS = RmiClient.kanbanManager.getStatus();
            this.TICKET_PRIORITY = RmiClient.kanbanManager.getPriorities();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        status.setItems(FXCollections.observableArrayList(TICKET_STATUS.values()));
        priority.setItems(FXCollections.observableArrayList(TICKET_PRIORITY.values()));
        status.getSelectionModel().select(TICKET_STATUS.get(0));
        priority.getSelectionModel().select(TICKET_PRIORITY.get(10));
    }

    public void save(ActionEvent actionEvent) {
        if (ticket == null) {
            try {
                RmiClient.kanbanManager.createTicket(title.getText(), description.getText(), 0, 10, null, project, null);
                stage.close();
                projectBoard.refresh(null);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (TicketExistsException e) {
                e.printStackTrace();
            }
        } else {
            try {
                ticket.setTitle(title.getText());
                ticket.setDescription(description.getText());
                RmiClient.kanbanManager.updateTicket(ticket);
                stage.close();
                projectBoard.refresh(null);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (TicketExistsException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancel(ActionEvent actionEvent) {
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        title.setText(ticket.getTitle());
        description.setText(ticket.getDescription());
        status.getSelectionModel().select(TICKET_STATUS.get(ticket.getStatus()));
        priority.getSelectionModel().select(TICKET_PRIORITY.get(ticket.getPriority()));
        if (ticket.getDue() != null)
            due.setValue(LocalDate.ofEpochDay(ticket.getDue().getTime()));
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setProjectBoard(TicketController ticketController) {
        this.projectBoard = ticketController;
    }
}
