package common.help;

public abstract class Builder<C, B extends Builder<C, B>> implements IBuilder<C, B> {
    protected abstract B _this();

    protected abstract C instance() throws Exception;

    @Override
    public C build() throws Exception {
        return instance();
    }
}
