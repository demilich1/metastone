package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpellTrigger implements Cloneable {
	private final static Logger logger = LoggerFactory.getLogger(SpellTrigger.class);

	private GameEventTrigger trigger;
	private Spell spell;
	private EntityReference hostReference;
	private final boolean oneTime;
	private boolean expired;
	private TriggerLayer layer = TriggerLayer.DEFAULT;

	public SpellTrigger(GameEventTrigger trigger, Spell spell) {
		this(trigger, spell, false);
	}

	public SpellTrigger(GameEventTrigger trigger, Spell spell, boolean oneTime) {
		this.trigger = trigger;
		this.spell = spell;
		this.oneTime = oneTime;
	}

	public SpellTrigger clone() {
		try {
			return (SpellTrigger) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public EntityReference getHostReference() {
		return hostReference;
	}

	public TriggerLayer getLayer() {
		return layer;
	}

	public int getOwner() {
		return trigger.getOwner();
	}
	
	protected Spell getSpell() {
		return spell;
	}

	public GameEventType interestedIn() {
		return trigger.interestedIn();
	}

	public boolean isExpired() {
		return expired;
	}

	public void onAdd(GameContext context) {
	}

	protected void onFire(int ownerId, Spell spell, GameEvent event) {
		event.getGameContext().getLogic().castSpell(ownerId, spell);
	}

	public void onGameEvent(GameEvent event) {
		int ownerId = trigger.getOwner();
		Entity host = event.getGameContext().resolveSingleTarget(ownerId, hostReference);
		try {
			if (!expired && trigger.fire(event, host)) {
				if (oneTime) {
					expired = true;
				}
				
				event.getGameContext().getEnvironment().put(Environment.EVENT_TARGET, event.getEventTarget());
				onFire(ownerId, spell, event);
				event.getGameContext().getEnvironment().remove(Environment.EVENT_TARGET);
			}
		} catch (Exception e) {
			logger.error("SpellTrigger cannot be executed; GameEventTrigger: {} Spell: {}", trigger, spell);
			throw e;
		}
	}
	
	public void onRemove(GameContext context) {
	}

	public void reset() {
		expired = false;
	}

	public void setHost(Entity host) {
		this.hostReference = host.getReference();
		spell.setSource(hostReference);
	}

	protected void setLayer(TriggerLayer layer) {
		this.layer = layer;
	}

	public void setOwner(int playerIndex) {
		trigger.setOwner(playerIndex);
	}

}
