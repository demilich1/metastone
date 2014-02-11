package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
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

	public int getOwner() {
		return trigger.getOwner();
	}

	public GameEventType interestedIn() {
		return trigger.interestedIn();
	}

	public void onGameEvent(IGameEvent event) {
		int ownerId = trigger.getOwner();
		//Entity host = event.getGameContext().resolveSingleTarget(ownerId, hostReference);
		Actor host = null;
		try {
			host = event.getGameContext().resolveSingleTarget(ownerId, hostReference);
		} catch (Exception e) {
			logger.error("Target not found for GameEventTrigger: {} Spell: {}", trigger, spell);
			throw e;
		}

		if (!expired && trigger.fire(event, host)) {
			if (!spell.hasPredefinedTarget()) {
				spell.setTarget(hostReference);
			}
			
			if (oneTime) {
				expired = true;
			}
			event.getGameContext().getLogic().castSpell(ownerId, spell);
		}
	}

	public void setHost(Entity host) {
		this.hostReference = host.getReference();
		spell.setSource(hostReference);
	}

	public void setOwner(int playerIndex) {
		trigger.setOwner(playerIndex);
	}

	public boolean isExpired() {
		return expired;
	}

}
