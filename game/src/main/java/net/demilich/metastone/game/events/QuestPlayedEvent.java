package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.QuestCard;
import net.demilich.metastone.game.entities.Entity;

public class QuestPlayedEvent extends GameEvent {

	private final QuestCard questCard;

	public QuestPlayedEvent(GameContext context, int playerId, QuestCard questCard) {
		super(context, playerId, -1);
		this.questCard = questCard;
	}
	
	@Override
	public Entity getEventTarget() {
		return getQuestCard();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SECRET_PLAYED;
	}

	public QuestCard getQuestCard() {
		return questCard;
	}

}
