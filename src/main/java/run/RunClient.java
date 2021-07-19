package run;

import implementation.SocketConnector;

import java.io.IOException;
import java.net.Socket;

public class RunClient extends Run {
    public static void main(String[] args) {

        try {
//            ServerSocket server = new ServerSocket(port);
            Socket client = new Socket(host, port);

            SocketConnector connector = SocketConnector.builder()
                    .setSocket(client)
                    .build();
            new RunClient().run(connector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
