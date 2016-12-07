package me.lejenome.kanban_board_lite.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable {
    // TODO: implement Object{In,out}putStream Socket connection with Request & Response objects

    private final Socket client;

    public SocketServer(Socket client) {
        this.client = client;
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket sc = new ServerSocket(8888)) {
            while (true) {
                Socket client = sc.accept();
                new Thread(new SocketServer(client)).run();
            }
        }
    }

    @Override
    public void run() {
        System.out.println("New client (" + client.getPort() + ") connected.");
        try {
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());

            // Request req = (Request) in.readObject();
            // Response res = new Response(SUCCESS);
            // out.writeObject(res);


        } catch (IOException e) {
            try {
                client.close();
            } catch (IOException e1) {
            }
        }

    }
}
