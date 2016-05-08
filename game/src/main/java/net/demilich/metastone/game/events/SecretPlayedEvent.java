package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.Entity;

public class SecretPlayedEvent extends GameEvent {

	private final SecretCard secretCard;

	public SecretPlayedEvent(GameContext context, int playerId, SecretCard secretCard) {
		super(context, playerId, -1);
		this.secretCard = secretCard;
	}
	
	@Override
	public Entity getEventTarget() {
		return getSecretCard();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SECRET_PLAYED;
	}

	public SecretCard getSecretCard() {
		return secretCard;
	}

}
