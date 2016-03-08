package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.Entity;

public class SecretRevealedEvent extends GameEvent {

	private final SecretCard secret;

	public SecretRevealedEvent(GameContext context, SecretCard secret, int playerId) {
		super(context, playerId, -1);
		this.secret = secret;
	}
	
	@Override
	public Entity getEventTarget() {
		return getSecret();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SECRET_REVEALED;
	}

	public SecretCard getSecret() {
		return secret;
	}

}
