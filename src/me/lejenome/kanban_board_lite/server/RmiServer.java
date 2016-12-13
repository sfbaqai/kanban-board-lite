package me.lejenome.kanban_board_lite.server;

import me.lejenome.kanban_board_lite.common.KanbanManager;
import me.lejenome.kanban_board_lite.server.db.Connection;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiServer {
    public static void main(String[] args) {
        try {
            Connection.initialize();
            KanbanManager stub = new KanbanManagerEngine();
            try {
                LocateRegistry.createRegistry(1099);
                //Registry reg = LocateRegistry.getRegistry();
                //reg.rebind("Account", stub);
                System.out.println("java RMI registry created.");
            } catch (RemoteException e) {
                System.out.println("java RMI registry already exists.");
            }
            Naming.rebind("//localhost/KanbanManager", stub);
            System.out.println("RMI Server Started...");
            NotificationProvider.getInstance();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
