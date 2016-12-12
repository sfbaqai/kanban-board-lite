package me.lejenome.kanban_board_lite.server;

import me.lejenome.kanban_board_lite.common.AccountExistsException;
import me.lejenome.kanban_board_lite.server.db.AccountEntity;
import me.lejenome.kanban_board_lite.server.db.Connection;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.Console;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Setup {

    public static void main(String[] args) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        String email, firstName, lastName, password;
        Scanner sc = new Scanner(System.in);
        System.out.println("#####     WELCOME TO KANBAN BOARD LITE     #####");
        System.out.println("That's One time Setup for the Server of Kanban Board Lite.");

        do {
            System.out.print("Admin Email: ");
            email = sc.nextLine().trim();
        } while (!emailValidator.isValid(email));

        do {
            System.out.print("Admin First Name: ");
            firstName = sc.nextLine().trim();
        } while (firstName.equals(""));

        do {
            System.out.print("Admin Last Name: ");
            lastName = sc.nextLine().trim();
        } while (lastName.equals(""));

        do {
            Console console = System.console();
            if (console != null) {
                password = new String(console.readPassword("Admin Password (at least 8 char): ")).trim();
            } else {
                System.out.print("Admin Password (at least 8 char): ");
                password = sc.nextLine().trim();
            }
        } while (password.length() < 8);

        System.out.println();
        System.out.println("Creating Admin Account:");
        System.out.println("    Email:      " + email);
        System.out.println("    First Name: " + firstName);
        System.out.println("    Last Name:  " + lastName);
        char yes = 'n';
        do {
            System.out.print("Confirm (Y/N) [Y] : ");
            try {
                yes = (char) System.in.read();
            } catch (IOException e) {
            }
        } while (Character.toLowerCase(yes) != 'y' && Character.toLowerCase(yes) != 'n');
        if (yes == 'n') {
            System.out.println("Abort...");
            System.exit(1);
        }
        try {
            Connection.initialize();
            AccountEntity acc = new AccountEntity(firstName, lastName, email, password);
            acc.setRole("admin");
            acc.save();
            System.out.println("Account Successfully Created.");
        } catch (SQLException e) {
            System.out.println("Error while Creating the Account! Abort...");
            System.exit(1);
        } catch (AccountExistsException e) {
            System.out.println("Account already exists! Abort...");
            System.exit(1);
        }
    }
}
