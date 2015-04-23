package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.DrawCardEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.TargetPlayer;

public class CardDrawnTrigger extends GameEventTrigger {

	private TargetPlayer targetPlayer;

	public CardDrawnTrigger(TargetPlayer targetPlayer) {
		this.targetPlayer = targetPlayer;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		DrawCardEvent drawEvent = (DrawCardEvent) event;

		switch (targetPlayer) {
		case BOTH:
			return true;
		case SELF:
			return drawEvent.getPlayerId() == host.getOwner();
		case OPPONENT:
			return drawEvent.getPlayerId() != host.getOwner();
		}

		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DRAW_CARD;
	}

}
