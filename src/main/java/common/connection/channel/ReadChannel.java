package common.connection.channel;

import common.connection.message.MessageContainer;

public class ReadChannel extends Channel implements IChannelRead {

    @Override
    public synchronized MessageContainer read() throws Exception {
        if (!this.queue.isEmpty()) {
            return this.queue.peek();
        } else {
            return null;
        }
    }

    @Override
    protected void remote() throws Exception {
        this.queue.add(this.remote.read());
    }
}
