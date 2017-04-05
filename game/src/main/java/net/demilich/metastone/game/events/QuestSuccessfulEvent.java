package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.QuestCard;
import net.demilich.metastone.game.entities.Entity;

public class QuestSuccessfulEvent extends GameEvent {

	private final QuestCard quest;

	public QuestSuccessfulEvent(GameContext context, QuestCard quest, int playerId) {
		super(context, playerId, -1);
		this.quest = quest;
	}
	
	@Override
	public Entity getEventTarget() {
		return getQuest();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SECRET_REVEALED;
	}

	public QuestCard getQuest() {
		return quest;
	}

}
