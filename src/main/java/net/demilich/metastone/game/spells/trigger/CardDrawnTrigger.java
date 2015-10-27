package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.DrawCardEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class CardDrawnTrigger extends GameEventTrigger {

	public CardDrawnTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		DrawCardEvent drawEvent = (DrawCardEvent) event;
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		CardType sourceType = (CardType) desc.get(EventTriggerArg.SOURCE_TYPE);
		if (sourceType != null && drawEvent.getSourceType() != sourceType) {
			return false;
		}
		switch (targetPlayer) {
		case BOTH:
			return true;
		case SELF:
		case OWNER:
			return drawEvent.getPlayerId() == host.getOwner();
		case OPPONENT:
			return drawEvent.getPlayerId() != host.getOwner();
		case ACTIVE:
			return drawEvent.getPlayerId() == event.getGameContext().getActivePlayerId();
		default:
			break;
		}

		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DRAW_CARD;
	}

}
