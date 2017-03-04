package net.demilich.metastone.game.actions;

/**
 * Created by bberman on 11/18/16.
 */
public class PlayCardException extends RuntimeException {
    public PlayCardException(Throwable innerException) {
        super(innerException);
    }
}
