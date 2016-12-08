package me.lejenome.kanban_board_lite.common;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

public interface KanbanManager extends Remote {
    Account authenticate(String email, String password) throws RemoteException, AccountNotFoundException, AuthenticationException;

    Account register(String firstName, String lastName, String email, String password) throws RemoteException, AccountExistsException;

    Account updateAccount(Account account) throws RemoteException, AccountExistsException;

    Project getProject(int id) throws RemoteException, ProjectNotFoundException;

    Project createProject(String name, String description, Account owner, Project parent) throws RemoteException, ProjectExistsException;

    Project updateProject(Project project) throws RemoteException, ProjectExistsException;

    Vector<Project> listProjects() throws RemoteException;

    Vector<Project> listProjects(Account owner) throws RemoteException;

    Vector<Ticket> listTickets(Project project) throws RemoteException;

    Vector<Ticket> listTickets(Project project, Account assignedTo) throws RemoteException;

    Ticket createTicket(String title, String description, int status, int priority, Account assignedTo, Project project, Date due) throws RemoteException, TicketExistsException;

    Ticket updateTicket(Ticket ticket) throws RemoteException, TicketExistsException;

    HashMap<Integer, String> getStatus() throws RemoteException;

    HashMap<Integer, String> getPriorities() throws RemoteException;
}
