package run;

import common.connection.channel.Connector;
import common.connection.channel.message.MessageContainer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Run {
    protected static Integer port = 1488;
    protected static String host = "localhost";

    protected Integer numberOfPeers = 5;
    protected Integer numberOfThreads = numberOfPeers * 2 + 1;
    protected ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);

    protected Connector connector;

    protected void run(Connector connector) {
        this.connector = connector;
        this.connector.start(this.threadPool);
        this.threadPool.execute(this::read);
        this.threadPool.execute(this::write);
    }

    protected void read() {
        while (true) {
            try {
                System.out.println(this.connector.read().getMessage(String.class));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void write() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(50);
                this.connector.write(MessageContainer.builder().setMessage(System.nanoTime() + " " + "QQL").build());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
