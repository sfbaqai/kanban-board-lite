package me.lejenome.kanban_board_lite.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by lejenome on 12/1/16.
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        try (Socket sc = new Socket(InetAddress.getLocalHost(), 8888)) {
            ObjectInputStream in = new ObjectInputStream(sc.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(sc.getOutputStream());
            // out.writeObject(new Request(??????));
            // Response res = (Response) in.readObject();
            // res.status // SUCCESS | FAILURE | DENIED
        }
    }
}
