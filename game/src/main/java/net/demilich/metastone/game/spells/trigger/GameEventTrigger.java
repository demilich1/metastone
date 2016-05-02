package net.demilich.metastone.game.spells.trigger;

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

	protected boolean determineTargetPlayer(GameEvent event, TargetPlayer targetPlayer, Entity host, int targetPlayerId) {
		if (targetPlayerId == -1 || targetPlayer == null) {
			return true;
		}
		switch (targetPlayer) {
		case ACTIVE:
			return event.getGameContext().getActivePlayerId() == targetPlayerId;
		case INACTIVE:
			return event.getGameContext().getActivePlayerId() != targetPlayerId;
		case BOTH:
			return true;
		case OPPONENT:
			return getOwner() != targetPlayerId;
		case OWNER:
			return host.getOwner() == targetPlayerId;
		case SELF:
			return getOwner() == targetPlayerId;
		default:
			break;
		}
		return false;
	}

	protected abstract boolean fire(GameEvent event, Entity host);

	public final boolean fires(GameEvent event, Entity host) {
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		if (targetPlayer != null && !determineTargetPlayer(event, targetPlayer, host, event.getTargetPlayerId())) {
			return false;
		}
		TargetPlayer sourcePlayer = desc.getSourcePlayer();
		if (sourcePlayer != null && !determineTargetPlayer(event, sourcePlayer, host, event.getSourcePlayerId())) {
			return false;
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
		Condition condition = (Condition) desc.get(EventTriggerArg.QUEUE_CONDITION);
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
	
	public boolean canFireCondition(GameEvent event) {
		Condition condition = (Condition) desc.get(EventTriggerArg.FIRE_CONDITION);
		Player owner = event.getGameContext().getPlayer(getOwner());
		if (condition != null && !condition.isFulfilled(event.getGameContext(), owner, event.getEventTarget())) {
			return false;
		}
		return true;
	}

}
