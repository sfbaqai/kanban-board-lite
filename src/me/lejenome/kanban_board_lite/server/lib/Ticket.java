package me.lejenome.kanban_board_lite.server.lib;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Created by lejenome on 11/29/16.
 */
public interface Ticket extends Remote {
    Ticket get(int id)  throws RemoteException;
    Vector<Ticket> all()  throws RemoteException;
    Vector<Ticket> all(Project p)  throws RemoteException;
}
