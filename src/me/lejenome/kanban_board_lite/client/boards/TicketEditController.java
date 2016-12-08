package me.lejenome.kanban_board_lite.client.boards;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    ComboBox<Integer> status;
    @FXML
    ComboBox<Integer> priority;
    @FXML
    DatePicker due;
    private Ticket ticket;
    private Project project;
    private HashMap<Integer, String> TICKET_STATUS;
    private HashMap<Integer, String> TICKET_PRIORITY;

    private TicketController projectBoard;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.TICKET_STATUS = RmiClient.kanbanManager.getStatus();
            this.TICKET_PRIORITY = RmiClient.kanbanManager.getPriorities();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        status.setButtonCell(new CheckBoxCellRenderer(TICKET_STATUS));
        status.setCellFactory(param -> new CheckBoxCellRenderer(TICKET_STATUS));
        priority.setButtonCell(new CheckBoxCellRenderer(TICKET_PRIORITY, 100));
        priority.setCellFactory(param -> new CheckBoxCellRenderer(TICKET_PRIORITY, 100));

        status.setItems(FXCollections.observableArrayList(TICKET_STATUS.keySet()));
        priority.setItems(FXCollections.observableArrayList(TICKET_PRIORITY.keySet()));
        status.getSelectionModel().select(Integer.valueOf(0));
        priority.getSelectionModel().select(Integer.valueOf(10));
    }

    public void save(ActionEvent actionEvent) {
        if (ticket == null) {
            try {
                RmiClient.kanbanManager.createTicket(title.getText(), description.getText(), status.getSelectionModel().getSelectedItem(), priority.getSelectionModel().getSelectedItem(), null, project, null);
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
        status.getSelectionModel().select(Integer.valueOf(ticket.getStatus()));
        priority.getSelectionModel().select(Integer.valueOf(ticket.getPriority()));
        if (ticket.getDue() != null)
            due.setValue(LocalDate.ofEpochDay(ticket.getDue().getTime()));
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setProjectBoard(TicketController ticketController) {
        this.projectBoard = ticketController;
    }

    class CheckBoxCellRenderer extends ListCell<Integer> {
        private final HashMap<Integer, String> map;
        private final int colorTo;

        public CheckBoxCellRenderer(HashMap<Integer, String> map) {
            this(map, 0);
        }

        public CheckBoxCellRenderer(HashMap<Integer, String> map, int colorTo) {
            this.map = map;
            this.colorTo = colorTo;
        }

        @Override
        protected void updateItem(Integer key, boolean empty) {
            super.updateItem(key, empty);
            if (key != null || empty) setText(map.get(key));
            if (colorTo > 0 && key != null) {
                if (key < colorTo / 4)
                    this.setStyle("-fx-border-color: green; -fx-border-width: 0 0 3 0;");
                else if (key < colorTo / 2)
                    this.setStyle("-fx-border-color: powderblue; -fx-border-width: 0 0 3 0;");
                else if (key < (colorTo / 4) * 3)
                    this.setStyle("-fx-border-color: coral; -fx-border-width: 0 0 3 0;");
                else
                    this.setStyle("-fx-border-color: red; -fx-border-width: 0 0 3 0;");
            }

        }
    }
}