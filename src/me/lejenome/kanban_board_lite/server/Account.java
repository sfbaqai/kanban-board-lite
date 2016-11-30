package me.lejenome.kanban_board_lite.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Account extends Remote {
    Account authenticate(String email, String password)  throws RemoteException;
    Account register(String firstName, String lastName, String email, String password) throws RemoteException;
}
