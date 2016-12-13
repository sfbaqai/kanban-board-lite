package me.lejenome.kanban_board_lite.client;

import javafx.application.Application;
import me.lejenome.kanban_board_lite.common.Account;
import me.lejenome.kanban_board_lite.common.KanbanManager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiClient {
    public static KanbanManager kanbanManager;
    public static Account account;

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {

        kanbanManager = (KanbanManager) Naming.lookup("//localhost/KanbanManager");

        NotificationWatcher.getInstance();

        Application.launch(App.class);
    }
}
