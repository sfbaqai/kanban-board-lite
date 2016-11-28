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
    }
}
