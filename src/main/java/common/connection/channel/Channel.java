package common.connection.channel;

import common.connection.connector.Connector;
import common.connection.connector.IConnector;
import common.connection.message.MessageContainer;
import common.help.Builder;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public abstract class Channel {
    protected Queue<MessageContainer> queue;
    protected IConnector local;
    protected IConnector remote;

    protected abstract void remote() throws Exception;

    public static abstract class ChannelBuilderAbstract<C extends Channel, B extends ChannelBuilderAbstract<C, B>>
            extends Builder<C, B> {
        protected Integer queueCapacity = 1;
        protected Connector local;
        protected Connector remote;

        public B setQueueCapacity(Integer queueCapacity) {
            this.queueCapacity = queueCapacity;
            return _this();
        }

        public B setLocal(Connector local) {
            this.local = local;
            return _this();
        }

        public B setRemote(Connector remote) {
            this.remote = remote;
            return _this();
        }

        @Override
        public C build() throws Exception {
            C instance = super.build();
            instance.queue = new ArrayBlockingQueue<>(queueCapacity);
            instance.local = local;
            instance.remote = remote;
            return instance;
        }
    }
}
