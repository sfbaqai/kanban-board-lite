package me.lejenome.kanban_board_lite;

import me.lejenome.kanban_board_lite.db.*;

public class Test {
    public static void main(String args[]) {
        Connection.initialize();
        Account acc = Account.create("moez", "bouhlel", "bmoez.j@gmail.com", "testtest");
        Account acc2 = Account.get("bmoez.j@gmail.com");
        Account acc3 = Account.authenticate("bmoez.j@gmail.com", "testtest");
        System.out.println(acc);
        System.out.println(acc2);
        System.out.println(acc3);
        Project p = Project.create("roadmap", "Roadmap or Porject", acc3, null);
        Project p2 = Project.get("roadmap");
        System.out.println(p);
        System.out.println(p2);
        System.out.println("List:");
        for(Project pe: Project.all())
            System.out.println(pe);
        for(int k: TICKET_PRIORITY.getInstance().keySet())
            System.out.println("PRIORITY: " + k + ": " + TICKET_PRIORITY.getInstance().get(k));
        for(int k: TICKET_STATUS.getInstance().keySet())
            System.out.println("STATUS : " + k + ": " + TICKET_STATUS.getInstance().get(k));
    }
}
