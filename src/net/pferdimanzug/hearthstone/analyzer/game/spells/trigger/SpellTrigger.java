package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.logic.CustomCloneable;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpellTrigger extends CustomCloneable implements IGameEventListener {
	private final static Logger logger = LoggerFactory.getLogger(SpellTrigger.class);

	private GameEventTrigger primaryTrigger;
	private GameEventTrigger secondaryTrigger;
	private SpellDesc spell;
	private EntityReference hostReference;
	private final boolean oneTime;
	private boolean expired;
	private TriggerLayer layer = TriggerLayer.DEFAULT;

	public SpellTrigger(GameEventTrigger primaryTrigger, GameEventTrigger secondaryTrigger, SpellDesc spell, boolean oneTime) {
		this.primaryTrigger = primaryTrigger;
		this.secondaryTrigger = secondaryTrigger;
		this.spell = spell;
		this.oneTime = oneTime;
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
		event.getGameContext().getLogic().castSpell(ownerId, spell);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if (expired) {
			return;
		}
		int ownerId = primaryTrigger.getOwner();
		try {
			Entity host = event.getGameContext().resolveSingleTarget(hostReference);
			if (triggerFires(primaryTrigger, event, host) || triggerFires(secondaryTrigger, event, host)) {
				if (oneTime) {
					expired = true;
				}

				spell.setSourceEntity(hostReference);
				event.getGameContext().getEnvironment().put(Environment.EVENT_TARGET, event.getEventTarget());
				onFire(ownerId, spell, event);
				event.getGameContext().getEnvironment().remove(Environment.EVENT_TARGET);
			}
		} catch (Exception e) {
			event.getGameContext().printCurrentTriggers();
			logger.error("SpellTrigger cannot be executed; GameEventTrigger: {} Spell: {}", primaryTrigger, spell);
			throw e;
		}
	}

	@Override
	public void onRemove(GameContext context) {
	}

	@Override
	public void setHost(Entity host) {
		this.hostReference = host.getReference();
		spell.setSourceEntity(hostReference);
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
				+ ", hostReference=" + hostReference + ", oneTime=" + oneTime + ", expired=" + expired + ", layer=" + layer + "]";
	}

	private boolean triggerFires(GameEventTrigger trigger, GameEvent event, Entity host) {
		if (trigger == null) {
			return false;
		}
		if (trigger.interestedIn() != event.getEventType() && trigger.interestedIn() != GameEventType.ALL) {
			return false;
		}
		return trigger.fire(event, host);
	}

}
