package shared;

public interface ThrowingConsumer<I, E extends Throwable> {
	public void apply(I input) throws E;
}
