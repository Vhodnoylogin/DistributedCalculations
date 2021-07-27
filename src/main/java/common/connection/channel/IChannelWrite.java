package common.connection.channel;

import common.connection.message.MessageContainer;

public interface IChannelWrite {
    void write(MessageContainer msg) throws Exception;
}
