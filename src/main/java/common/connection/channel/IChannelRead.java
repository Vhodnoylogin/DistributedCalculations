package common.connection.channel;

import common.connection.message.MessageContainer;

public interface IChannelRead {
    MessageContainer read() throws Exception;
}
