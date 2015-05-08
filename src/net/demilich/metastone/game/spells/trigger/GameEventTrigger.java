package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.logic.CustomCloneable;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

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

	public final boolean fires(GameEvent event, Entity host) {
		boolean ownTurnOnly = desc.getBool(EventTriggerArg.OWN_TURN_ONLY);
		if (ownTurnOnly && event.getGameContext().getActivePlayerId() != getOwner()) {
			return false;
		}
		boolean breaksStealth = desc.getBool(EventTriggerArg.BREAKS_STEALTH);
		if (breaksStealth) {
			event.getGameContext().getLogic().removeTag(host, GameTag.STEALTH);
		}
		return fire(event, host);
	}

	protected abstract boolean fire(GameEvent event, Entity host);

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
