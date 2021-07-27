package common.connection.channel;

import common.connection.message.MessageContainer;

public class WriteChannel extends Channel implements IChannelWrite {
    @Override
    protected void remote() throws Exception {
        if (!this.queue.isEmpty()) {
            this.remote.write(this.queue.peek());
        }
    }

    @Override
    public synchronized void write(MessageContainer msg) throws Exception {
//        this.queue.add(msg);
        this.remote.write(msg);
    }
}
