package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.logic.CustomCloneable;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.condition.Condition;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;
import net.demilich.metastone.game.targeting.TargetType;

public abstract class GameEventTrigger extends CustomCloneable {

	private int owner = -1;
	protected final EventTriggerDesc desc;

	public GameEventTrigger(EventTriggerDesc desc) {
		this.desc = desc;
	}

	@Override
	public GameEventTrigger clone() {
		return (GameEventTrigger) super.clone();
	}

	public boolean determineTargetPlayer(GameEvent event, TargetPlayer targetPlayer, Entity host, int playerId) {
		switch (targetPlayer) {
		case ACTIVE:
			return event.getGameContext().getActivePlayerId() == playerId;
		case INACTIVE:
			return event.getGameContext().getActivePlayerId() != playerId;
		case BOTH:
			return true;
		case OPPONENT:
			return host.getOwner() != playerId;
		case OWNER:
		case SELF:
			return host.getOwner() == playerId;
		default:
			break;
		}
		return false;
	}

	protected abstract boolean fire(GameEvent event, Entity host);

	public final boolean fires(GameEvent event, Entity host) {
		boolean ownTurnOnly = desc.getBool(EventTriggerArg.OWN_TURN_ONLY);
		if (ownTurnOnly && event.getGameContext().getActivePlayerId() != getOwner()) {
			return false;
		}
		boolean breaksStealth = desc.getBool(EventTriggerArg.BREAKS_STEALTH);
		if (breaksStealth) {
			event.getGameContext().getLogic().removeAttribute(host, Attribute.STEALTH);
		}
		
		TargetType hostTargetType = (TargetType) desc.get(EventTriggerArg.HOST_TARGET_TYPE);
		if (hostTargetType == TargetType.IGNORE_AS_TARGET && event.getEventTarget() == host) {
			return false;
		} else if (hostTargetType == TargetType.IGNORE_AS_SOURCE && event.getEventSource() == host) {
			return false;
		} else if (hostTargetType == TargetType.IGNORE_OTHER_TARGETS && event.getEventTarget() != host) {
			return false;
		} else if (hostTargetType == TargetType.IGNORE_OTHER_SOURCES && event.getEventSource() != host) {
			return false;
		}
		
		Condition condition = (Condition) desc.get(EventTriggerArg.CONDITION);
		Player owner = event.getGameContext().getPlayer(getOwner());
		if (condition != null && !condition.isFulfilled(event.getGameContext(), owner, event.getEventTarget())) {
			return false;
		}
		return fire(event, host);
	}

	public int getOwner() {
		return owner;
	}

	public abstract GameEventType interestedIn();

	public void setOwner(int playerIndex) {
		this.owner = playerIndex;
	}

	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + " owner:" + owner + "]";
	}

}
