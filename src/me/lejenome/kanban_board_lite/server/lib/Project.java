package me.lejenome.kanban_board_lite.server.lib;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface Project extends Remote {
    Project get(int id)  throws RemoteException;
    Vector<Project> all()  throws RemoteException;
    Vector<Project> all(Account owner)  throws RemoteException;
}
