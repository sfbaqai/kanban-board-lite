package me.lejenome.kanban_board_lite.client;

import me.lejenome.kanban_board_lite.server.Account;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by lejenome on 11/30/16.
 */
public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {

        Account acc = (Account) Naming.lookup("//localhost/Account");
        acc.authenticate("bmoez.j@gmail", "testtest");
        acc.register("moez", "bouhlel", "bmoez.j@gmail.com", "testtest");

    }
}
