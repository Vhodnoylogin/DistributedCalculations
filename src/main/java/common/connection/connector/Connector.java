package common.connection.connector;

import common.connection.message.MessageContainer;
import common.help.Builder;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public abstract class Connector implements IConnector {
    protected BlockingQueue<MessageContainer> queueRead;
    protected BlockingQueue<MessageContainer> queueWrite;

    public void start(ExecutorService executor) {
        executor.execute(this::sendWrite);
        executor.execute(this::getRead);
    }

    public void write(MessageContainer message) throws InterruptedException {
        this.queueWrite.put(message);
    }

    public MessageContainer read() throws InterruptedException {
        return this.queueRead.take();
    }

    protected void sendWrite() {
        while (true) {
            for (MessageContainer message : this.queueWrite) {
                try {
                    sendToRemote(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    protected void getRead() {
        while (true) {
            try {
                this.queueRead.put(getFromRemote());
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void sendToRemote(MessageContainer msg) throws IOException;

    protected abstract MessageContainer getFromRemote() throws IOException, ClassNotFoundException;


    public static abstract class ConnectorBuilderAbstract<C extends Connector, B extends ConnectorBuilderAbstract<C, B>>
            extends Builder<C, B> {

        @Override
        public C build() throws Exception {
            C instance = super.build();
            instance.queueRead = new ArrayBlockingQueue<>(100);
            instance.queueWrite = new ArrayBlockingQueue<>(100);
            return instance;
        }
    }
}
