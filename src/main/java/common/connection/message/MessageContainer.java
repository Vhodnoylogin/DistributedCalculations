package common.connection.message;

import common.help.Builder;

import java.io.*;

public class MessageContainer implements Serializable {
    protected Object message;

    public static MessageContainerBuilder builder() {
        return new MessageContainerBuilder();
    }

    public static <T extends MessageContainer> T deserialize(InputStream in, Class<T> clazz)
            throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(in);
        return clazz.cast(inputStream.readObject());
    }

    public static <T extends MessageContainer> void serialize(T msg, OutputStream out) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(out);
        outputStream.writeObject(msg);
    }

    public <T> T getMessage(Class<T> clazz) throws ClassCastException {
        return clazz.cast(this.message);
    }

    public static abstract class MessageContainerBuilderAbstract<
            C extends MessageContainer
            , B extends MessageContainerBuilderAbstract<C, B>>
            extends Builder<C, B> {
        protected Object message;

        public B setMessage(Object msg) {
            this.message = msg;
            return _this();
        }

        @Override
        public C build() {
            try {
                C instance = super.build();
                instance.message = this.message;
                return instance;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class MessageContainerBuilder extends MessageContainerBuilderAbstract<MessageContainer, MessageContainerBuilder> {
        @Override
        protected MessageContainerBuilder _this() {
            return this;
        }

        @Override
        protected MessageContainer instance() {
            return new MessageContainer();
        }


    }
}
