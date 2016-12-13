package me.lejenome.kanban_board_lite.server;

import me.lejenome.kanban_board_lite.server.db.ProjectEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationProvider implements Runnable {
    // TODO: implement Object{In,out}putStream Socket connection with Request & Response objects

    public static NotificationProvider instance;
    public ServerSocket server;

    public ConcurrentHashMap<Socket, PrintWriter> subscriptions;

    private NotificationProvider() {
        subscriptions = new ConcurrentHashMap<>();
        try {
            server = new ServerSocket(9999);
            new Thread(this).start();
            System.out.println("Listening for Event Subscriptions...");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static NotificationProvider getInstance() {
        if (instance == null) {
            instance = new NotificationProvider();
        }
        return instance;
    }

    public static void notifyProjectUpdate(int projectId) {
        System.out.println("Sending new notification: " + projectId);
        for (Map.Entry<Socket, PrintWriter> entry : getInstance().subscriptions.entrySet()) {
            entry.getValue().write("Project:" + projectId + "\n");
            entry.getValue().flush();
        }
    }

    public static void notifyProjectUpdate(ProjectEntity p) {
        notifyProjectUpdate(p.getId());
    }

    @Override
    public void run() {

        while (true) {
            try {
                Socket client = server.accept();
                System.out.println("New subscriper (" + client.getPort() + ").");
                subscriptions.put(client, new PrintWriter(client.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
