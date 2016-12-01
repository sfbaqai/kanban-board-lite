package me.lejenome.kanban_board_lite.server.lib;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AccountRMI extends UnicastRemoteObject implements Account {

    public AccountRMI() throws RemoteException {
    }

    @Override
    public Account authenticate(String email, String password) throws RemoteException {
        System.out.println("Account.autherticate()");
        return null;
    }

    @Override
    public Account register(String firstName, String lastName, String email, String password) throws RemoteException {
        System.out.println("Account.register()");
        return null;
    }
}
