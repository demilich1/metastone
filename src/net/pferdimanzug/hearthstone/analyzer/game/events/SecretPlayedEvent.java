package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SecretPlayedEvent extends GameEvent {

	private final SecretCard secretCard;

	public SecretPlayedEvent(GameContext context, SecretCard secretCard) {
		super(context);
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
