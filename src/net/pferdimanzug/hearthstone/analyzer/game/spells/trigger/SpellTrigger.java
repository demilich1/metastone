package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class SpellTrigger implements IGameEventListener {
	
	private final IGameEventTrigger trigger;
	private final ISpell spell;
	private Entity host;

	public SpellTrigger(IGameEventTrigger trigger, ISpell spell) {
		this.trigger = trigger;
		this.spell = spell;
	}

	@Override
	public void onGameEvent(IGameEvent event) {
		if (trigger.fire(event, host)) {
			event.getGameContext().getLogic().castSpell(host.getOwner(), spell, trigger.getTarget());
		}
	}

	@Override
	public GameEventType interestedIn() {
		return trigger.interestedIn();
	}

	public Entity getHost() {
		return host;
	}

	public void setHost(Entity host) {
		this.host = host;
	}

}
