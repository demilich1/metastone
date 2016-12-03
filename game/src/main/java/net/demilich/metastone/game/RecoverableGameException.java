package net.demilich.metastone.game;

/**
 * Created by bberman on 12/2/16.
 */
public class RecoverableGameException extends RuntimeException {
	private final GameContext context;

	public RecoverableGameException(GameContext context, Throwable cause) {
		super(cause);
		this.context = context.clone();
	}

	public RecoverableGameException(String message, GameContext context, Throwable cause) {
		super(message, cause);
		this.context = context.clone();
	}

	public RecoverableGameException(String message, GameContext context) {
		super(message);
		this.context = context.clone();
	}

	@Override
	public String getMessage() {
		return super.getMessage() + "\nGameContext:\n" + context.toLongString();
	}
}
