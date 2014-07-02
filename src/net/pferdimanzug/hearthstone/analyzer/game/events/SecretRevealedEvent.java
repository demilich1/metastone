package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SecretRevealedEvent extends GameEvent {

	private final SecretCard secret;
	private final int playerId;

	public SecretRevealedEvent(GameContext context, SecretCard secret, int playerId) {
		super(context);
		this.secret = secret;
		this.playerId = playerId;
	}

	@Override
	public Entity getEventTarget() {
		return getSecret();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SECRET_REVEALED;
	}

	public int getPlayerId() {
		return playerId;
	}

	public SecretCard getSecret() {
		return secret;
	}

}
