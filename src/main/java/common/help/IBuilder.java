package common.help;

public interface IBuilder<C, B extends IBuilder<C, B>> {
    C build() throws Exception;
}
