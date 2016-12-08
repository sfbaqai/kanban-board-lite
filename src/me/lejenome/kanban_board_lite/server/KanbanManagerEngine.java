package me.lejenome.kanban_board_lite.server;

import me.lejenome.kanban_board_lite.common.*;
import me.lejenome.kanban_board_lite.server.db.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

public class KanbanManagerEngine extends UnicastRemoteObject implements KanbanManager {

    protected KanbanManagerEngine() throws RemoteException {
    }

    @Override
    public Account authenticate(String email, String password) throws AccountNotFoundException, AuthenticationException {
        return AccountEntity.authenticate(email, password);
    }

    @Override
    public Account register(String firstName, String lastName, String email, String password) throws AccountExistsException {
        AccountEntity acc = new AccountEntity(firstName, lastName, email, password);
        acc.save();
        return acc;
    }

    @Override
    public Account updateAccount(Account account) throws AccountExistsException {
        ((AccountEntity) account).save();
        return account;
    }

    @Override
    public Project getProject(int id) throws RemoteException, ProjectNotFoundException {
        return ProjectEntity.get(id);
    }

    @Override
    public Project createProject(String name, String description, Account owner, Project parent) throws ProjectExistsException {
        ProjectEntity p = new ProjectEntity(name, description, (AccountEntity) owner, (ProjectEntity) parent);
        p.save();
        return p;
    }

    @Override
    public Project updateProject(Project project) throws ProjectExistsException {
        ((ProjectEntity) project).save();
        return project;
    }

    @Override
    public Vector<Project> listProjects() {

        Vector<ProjectEntity> l = ProjectEntity.all();
        Vector<Project> v = new Vector<>(l);
        return v;
    }

    @Override
    public Vector<Project> listProjects(Account owner) {
        Vector<ProjectEntity> l = ProjectEntity.all((AccountEntity) owner);
        Vector<Project> v = new Vector<>(l);
        return v;
    }

    @Override
    public Vector<Ticket> listTickets(Project project) {
        Vector<TicketEntity> l = TicketEntity.all((ProjectEntity) project);
        Vector<Ticket> v = new Vector<>(l);
        return v;

    }

    @Override
    public Vector<Ticket> listTickets(Project project, Account assignedTo) {
        Vector<TicketEntity> l = TicketEntity.all((ProjectEntity) project, (AccountEntity) assignedTo);
        Vector<Ticket> v = new Vector<>(l);
        return v;
    }

    @Override
    public Ticket createTicket(String title, String description, int status, int priority, Account owner, Project project, Date due) throws TicketExistsException {
        TicketEntity t = new TicketEntity(title, description, status, priority, project, owner, due);
        t.save();
        return t;
    }

    @Override
    public Ticket updateTicket(Ticket ticket) throws TicketExistsException {
        ((TicketEntity) ticket).save();
        return ticket;
    }

    @Override
    public HashMap<Integer, String> getStatus() throws RemoteException {
        return TICKET_STATUS.getInstance();
    }

    @Override
    public HashMap<Integer, String> getPriorities() throws RemoteException {
        return TICKET_PRIORITY.getInstance();
    }
}
