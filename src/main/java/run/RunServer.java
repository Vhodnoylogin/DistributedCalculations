package run;

import implementation.SocketConnector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RunServer extends Run {
    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(port);
            Socket client = server.accept();

            SocketConnector connector = SocketConnector.builder()
                    .setSocket(client)
                    .build();
            new RunServer().run(connector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
