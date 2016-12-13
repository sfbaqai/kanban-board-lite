package me.lejenome.kanban_board_lite.client;

import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

/**
 * Copyright (c) Moez Bouhlel <bmoez.j@gmail.com>
 * License: GNU GENERAL PUBLIC LICENSE 3 (GPL3)
 */
public class NotificationWatcher implements Runnable {
    public static NotificationWatcher instance;
    Socket socket;
    Scanner in;
    Vector<NotificationHandler> handlers;

    private NotificationWatcher() {

        try {
            handlers = new Vector<>();
            socket = new Socket("127.0.0.1", 9999);
            in = new Scanner(socket.getInputStream());
            System.out.println("Listening for Event Provider.");
            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static NotificationWatcher getInstance() {
        if (instance == null) {
            instance = new NotificationWatcher();
        }
        return instance;
    }

    public static void close() {
        if (instance != null) {
            try {
                instance.socket.close();
                instance = null;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addListener(NotificationHandler handler) {
        getInstance().handlers.add(handler);
    }

    @Override
    public void run() {
        while (true) {
            String notification = in.nextLine();
            String[] data = notification.split(":");
            System.out.println("New Event: " + data[0] + " (" + data[1] + ")");
            Platform.runLater(() -> {
                for (
                        NotificationHandler handler : handlers)
                    handler.handle(data[0], data[1]);
            });
        }
    }

}
