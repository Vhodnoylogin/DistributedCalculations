package implementation;

import common.connection.connector.Connector;
import common.connection.message.MessageContainer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketConnector extends Connector {
    protected InputStream in;
    protected OutputStream out;

//    protected BufferedReader reader;
//    protected BufferedWriter writer;

    public static SocketConnectorBuilder builder() {
        return new SocketConnectorBuilder();
    }

    @Override
    protected void sendToRemote(MessageContainer msg) throws IOException {
        MessageContainer.serialize(msg, this.out);
    }

    @Override
    protected MessageContainer getFromRemote() throws IOException {
        try {
            return MessageContainer.deserialize(this.in, MessageContainer.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static abstract class SocketConnectorBuilderAbstract<
            C extends SocketConnector
            , B extends SocketConnectorBuilderAbstract<C, B>
            > extends ConnectorBuilderAbstract<C, B> {
        protected Socket socket;

        public B setSocket(Socket socket) {
            this.socket = socket;
            return _this();
        }

        @Override
        public C build() throws IOException {
            try {
                C instance = super.build();
                instance.in = this.socket.getInputStream();
                instance.out = this.socket.getOutputStream();
//                instance.reader = new BufferedReader(new InputStreamReader(instance.in));
//                instance.writer = new BufferedWriter(new OutputStreamWriter(instance.out));
                return instance;
            } catch (IOException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class SocketConnectorBuilder extends SocketConnectorBuilderAbstract<SocketConnector, SocketConnectorBuilder> {

        @Override
        protected SocketConnectorBuilder _this() {
            return this;
        }

        @Override
        protected SocketConnector instance() {
            return new SocketConnector();
        }
    }
}
