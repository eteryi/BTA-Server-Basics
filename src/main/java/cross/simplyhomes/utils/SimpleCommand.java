package cross.simplyhomes.utils;

public interface SimpleCommand extends TCommand {
	@Override
	default boolean isOp() {
		return false;
	};
}
