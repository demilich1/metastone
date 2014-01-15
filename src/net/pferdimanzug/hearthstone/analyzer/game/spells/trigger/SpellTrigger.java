package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;

public class SpellTrigger implements IGameEventListener, Cloneable {

	private final GameEventTrigger trigger;
	private final Spell spell;
	private TargetKey hostKey;

	public SpellTrigger(GameEventTrigger trigger, Spell spell) {
		this.trigger = trigger;
		this.spell = spell;
	}

	@Override
	public GameEventType interestedIn() {
		return trigger.interestedIn();
	}

	@Override
	public void onGameEvent(IGameEvent event) {
		Player owner = event.getGameContext().getPlayer(trigger.getOwner());
		Entity host = event.getGameContext().resolveSingleTarget(owner, hostKey);
		if (trigger.fire(event, host)) {
			if (!spell.hasPredefinedTarget()) {
				spell.setTarget(hostKey);
			}
			
			event.getGameContext().getLogic().castSpell(owner, spell);
		}
	}

	public void setHost(Entity host) {
		this.hostKey = TargetKey.pointTo(host);
	}

	public int getOwner() {
		return trigger.getOwner();
	}

	public void setOwner(int playerIndex) {
		trigger.setOwner(playerIndex);
	}
	
	public SpellTrigger clone() {
		try {
			return (SpellTrigger) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
