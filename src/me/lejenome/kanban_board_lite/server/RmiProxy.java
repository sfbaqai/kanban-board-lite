package me.lejenome.kanban_board_lite.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiProxy {
    public static void main(String[] args) {

        try {
            Account stub = new AccountRMI();
            try {
                LocateRegistry.createRegistry(1099);
                //Registry reg = LocateRegistry.getRegistry();
                //reg.rebind("Account", stub);
                System.out.println("java RMI registry created.");
            } catch (RemoteException e) {
                System.out.println("java RMI registry already exists.");
            }
            Naming.rebind("//localhost/Account", stub);
            System.out.println("Account RMI Server Started...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
