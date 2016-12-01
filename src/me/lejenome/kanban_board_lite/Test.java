package me.lejenome.kanban_board_lite;

import me.lejenome.kanban_board_lite.common.*;
import me.lejenome.kanban_board_lite.server.db.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
    public static void main(String args[]) throws SQLException {

        Connection.initialize();

        AccountEntity acc = new AccountEntity("moez", "bouhlel", "bmoez.j@gmail.com", "testtest");
        try {
            acc.save();
            System.out.println(acc);
        } catch (AccountExistsException e) {
        }
        try {
            AccountEntity acc2 = AccountEntity.get("bmoez.j@gmail.com");
            System.out.println(acc2);
        } catch (AccountNotFoundException e) {
        }

        try {
            AccountEntity acc3 = AccountEntity.authenticate("bmoez.j@gmail.com", "testtest");
            System.out.println(acc3);
            ProjectEntity p = new ProjectEntity("roadmap", "Roadmap or Porject", acc3, null);
            p.save();
            System.out.println(p);
        } catch (AccountNotFoundException | AuthenticationException | ProjectExistsException e) {
        }

        try {
            ProjectEntity p2 = ProjectEntity.get(1);
            System.out.println(p2);
        } catch (ProjectNotFoundException e) {
        }
        System.out.println("List:");
        for (ProjectEntity pe : ProjectEntity.all())
            System.out.println(pe);
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
    }
}
