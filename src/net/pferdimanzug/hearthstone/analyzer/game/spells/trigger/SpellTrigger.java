package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class SpellTrigger implements IGameEventListener {
	
	private final GameEventTrigger trigger;
	private final ISpell spell;
	private Entity host;

	public SpellTrigger(GameEventTrigger trigger, ISpell spell) {
		this.trigger = trigger;
		this.spell = spell;
	}

	public Entity getHost() {
		return host;
	}

	@Override
	public GameEventType interestedIn() {
		return trigger.interestedIn();
	}

	@Override
	public void onGameEvent(IGameEvent event) {
		if (trigger.fire(event, host)) {
			Entity target = trigger.getTarget(event.getGameContext(), host);
			event.getGameContext().getLogic().castSpell(host.getOwner(), spell, target);
		}
	}

	public void setHost(Entity host) {
		this.host = host;
	}

	public Player getOwner() {
		return trigger.getOwner();
	}

	public void setOwner(Player owner) {
		trigger.setOwner(owner);
	}

}
