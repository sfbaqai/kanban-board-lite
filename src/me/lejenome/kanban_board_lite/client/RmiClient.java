package me.lejenome.kanban_board_lite.client;

import javafx.application.Application;
import me.lejenome.kanban_board_lite.common.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiClient {
    public static KanbanManager kanbanManager;

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {

        kanbanManager = (KanbanManager) Naming.lookup("//localhost/KanbanManager");

        Application.launch(App.class);
        try {
            Account acc = kanbanManager.register("moez", "bouhlel", "bmoez.j@gmail.com", "testtest");
            System.out.println("Register: " + acc);
        } catch (AccountExistsException e) {
            System.out.println("Account (bmoez.j@gmail.com) Already Exists!");
        }

        try {
            Account acc3 = kanbanManager.authenticate("bmoez.j@gmail.com", "testtest");
            System.out.println("Authenticate: " + acc3);
            Project p = kanbanManager.createProject("roadmap", "Roadmap or Porject", acc3, null);
            System.out.println("Create Project: " + p);
        } catch (AccountNotFoundException | AuthenticationException | ProjectExistsException e) {
        }

        try {
            Project p2 = kanbanManager.getProject(1);
            System.out.println("Get Project: " + p2);
        } catch (ProjectNotFoundException e) {
        }

        System.out.println("List Projects:");
        for (Project pe : kanbanManager.listProjects())
            System.out.println("+ " + pe);

        /*
        System.out.println("List:");
        for (int k : TICKET_PRIORITY.getInstance().keySet())
            System.out.println("PRIORITY: " + k + ": " + TICKET_PRIORITY.getInstance().get(k));
        for (int k : TICKET_STATUS.getInstance().keySet())
            System.out.println("STATUS : " + k + ": " + TICKET_STATUS.getInstance().get(k));
        try {
            ResultSet res = Connection.executeInsert("Insert Into Project (name, description, owner, parent) Values ('test5', 'de sc rpt', 14, NULL)");
            while (res.next()) {
                System.out.println(res.getInt("id"));
            }
        } catch (Exception e) {
        }
        */
    }
}
