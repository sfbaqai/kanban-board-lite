package me.lejenome.kanban_board_lite.client.boards;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import me.lejenome.kanban_board_lite.client.RmiClient;
import me.lejenome.kanban_board_lite.common.Ticket;
import me.lejenome.kanban_board_lite.common.TicketExistsException;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Created by lejenome on 12/12/16.
 */
public class TicketItem extends AnchorPane {

    @FXML
    Label title;

    @FXML
    Label due;
    @FXML
    Label assignedTo;
    @FXML
    Pane priority;
    @FXML
    ImageView dueIcon;
    private Ticket ticket;


    public TicketItem(Ticket ticket) {
        this();
        setTicket(ticket);
    }

    public TicketItem() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ticketItem.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        title.setText(ticket.getTitle());
        if (ticket.getDue() != null) {
            due.setText(ticket.getDue().toString());
        } else {
            due.setText("");
            dueIcon.setVisible(false);
        }
        if (ticket.getAssignedToId() > 0) {
            assignedTo.setText(String.valueOf(ticket.getAssignedToId()).substring(0, 1));
        } else {
            assignedTo.setText("?");
        }


        if (ticket.getPriority() < 100 / 4)
            priority.setStyle("-fx-background-color: green;");
        else if (ticket.getPriority() < 100 / 2)
            priority.setStyle("-fx-background-color: powderblue;");
        else if (ticket.getPriority() < (100 / 4) * 3)
            priority.setStyle("-fx-background-color: coral;");
        else
            priority.setStyle("-fx-background-color: red;");
    }

    @FXML
    public void assign(MouseEvent e) {
        try {
            if (ticket.getAssignedToId() < 1) {
                ticket.setAssignedTo(RmiClient.account);
                assignedTo.setText(String.valueOf(ticket.getAssignedToId()).substring(0, 1));

                RmiClient.kanbanManager.updateTicket(ticket);

            } else if (ticket.getAssignedToId() == RmiClient.account.getId()) {
                ticket.setAssignedTo(null);
                assignedTo.setText("?");
                RmiClient.kanbanManager.updateTicket(ticket);
            }
        } catch (RemoteException | TicketExistsException e1) {
            e1.printStackTrace();
        }

    }
}
