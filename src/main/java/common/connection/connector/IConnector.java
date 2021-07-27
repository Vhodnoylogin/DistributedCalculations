package common.connection.connector;

import common.connection.message.MessageContainer;

public interface IConnector {
    MessageContainer read() throws Exception;

    void write(MessageContainer msg) throws Exception;
}
