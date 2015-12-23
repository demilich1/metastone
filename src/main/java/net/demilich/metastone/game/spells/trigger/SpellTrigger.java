package net.demilich.metastone.game.spells.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.logic.CustomCloneable;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class SpellTrigger extends CustomCloneable implements IGameEventListener {
	private final static Logger logger = LoggerFactory.getLogger(SpellTrigger.class);

	private GameEventTrigger primaryTrigger;
	private GameEventTrigger secondaryTrigger;
	private SpellDesc spell;
	private EntityReference hostReference;
	private final boolean oneTurn;
	private boolean expired;
	private TriggerLayer layer = TriggerLayer.DEFAULT;

	public SpellTrigger(GameEventTrigger primaryTrigger, GameEventTrigger secondaryTrigger, SpellDesc spell, boolean oneTurn) {
		this.primaryTrigger = primaryTrigger;
		this.secondaryTrigger = secondaryTrigger;
		this.spell = spell;
		this.oneTurn = oneTurn;
	}

	public SpellTrigger(GameEventTrigger trigger, SpellDesc spell) {
		this(trigger, spell, false);
	}

	public SpellTrigger(GameEventTrigger trigger, SpellDesc spell, boolean oneTime) {
		this(trigger, null, spell, oneTime);
	}

	@Override
	public SpellTrigger clone() {
		SpellTrigger clone = (SpellTrigger) super.clone();
		clone.primaryTrigger = (GameEventTrigger) primaryTrigger.clone();
		if (secondaryTrigger != null) {
			clone.secondaryTrigger = (GameEventTrigger) secondaryTrigger.clone();
		}
		clone.spell = spell.clone();
		return clone;
	}
	
	protected void expire() {
		expired = true;
	}

	@Override
	public EntityReference getHostReference() {
		return hostReference;
	}

	@Override
	public TriggerLayer getLayer() {
		return layer;
	}

	@Override
	public int getOwner() {
		return primaryTrigger.getOwner();
	}

	protected SpellDesc getSpell() {
		return spell;
	}

	@Override
	public boolean interestedIn(GameEventType eventType) {
		if (oneTurn && eventType == GameEventType.TURN_END && primaryTrigger.interestedIn() != GameEventType.TURN_START) {
			return true;
		}
		
		boolean result = primaryTrigger.interestedIn() == eventType || primaryTrigger.interestedIn() == GameEventType.ALL;
		if (secondaryTrigger != null) {
			result |= secondaryTrigger.interestedIn() == eventType || secondaryTrigger.interestedIn() == GameEventType.ALL;
		}
		return result;
	}

	@Override
	public boolean isExpired() {
		return expired;
	}

	@Override
	public void onAdd(GameContext context) {
	}

	protected void onFire(int ownerId, SpellDesc spell, GameEvent event) {
		event.getGameContext().getLogic().castSpell(ownerId, spell, hostReference, null, true);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if (expired) {
			return;
		}
		
		int ownerId = primaryTrigger.getOwner();
		try {
			event.getGameContext().getEnvironment().put(Environment.EVENT_TARGET, event.getEventTarget());
			onFire(ownerId, spell, event);
			event.getGameContext().getEnvironment().remove(Environment.EVENT_TARGET);
			if (event.getEventType() == GameEventType.TURN_START) {
				expire();
			}
		} catch (Exception e) {
			event.getGameContext().printCurrentTriggers();
			logger.error("SpellTrigger cannot be executed; GameEventTrigger: {} Spell: {}", primaryTrigger, spell);
			throw e;
		}
		
		if (oneTurn && event.getEventType() == GameEventType.TURN_END && primaryTrigger.interestedIn() != GameEventType.TURN_START) {
			expire();
		}
	}

	@Override
	public void onRemove(GameContext context) {
	}

	@Override
	public void setHost(Entity host) {
		this.hostReference = host.getReference();
	}

	protected void setLayer(TriggerLayer layer) {
		this.layer = layer;
	}

	@Override
	public void setOwner(int playerIndex) {
		primaryTrigger.setOwner(playerIndex);
		if (secondaryTrigger != null) {
			secondaryTrigger.setOwner(playerIndex);
		}
	}

	@Override
	public String toString() {
		return "[SpellTrigger primaryTrigger=" + primaryTrigger + ", secondaryTrigger=" + secondaryTrigger + ", spell=" + spell
				+ ", hostReference=" + hostReference + ", oneTurn=" + oneTurn + ", expired=" + expired + ", layer=" + layer + "]";
	}
	
	@Override
	public boolean canFire(GameEvent event) {
		Entity host = event.getGameContext().resolveSingleTarget(hostReference);
		return (triggerFires(primaryTrigger, event, host) || triggerFires(secondaryTrigger, event, host));
	}

	private boolean triggerFires(GameEventTrigger trigger, GameEvent event, Entity host) {
		if (trigger == null) {
			return false;
		}
		if (trigger.interestedIn() != event.getEventType() && trigger.interestedIn() != GameEventType.ALL) {
			return false;
		}
		return trigger.fires(event, host);
	}

}
