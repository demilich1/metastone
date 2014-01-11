package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class TurnEndEventlistener implements IGameEventListener {
	
	private ISpell spell;
	private Entity target;

	public TurnEndEventlistener(ISpell spell, Entity target) {
		this.spell = spell;
		this.target = target;
		
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.TURN_END;
	}

	@Override
	public void onGameEvent(IGameEvent event) {
		TurnEndEvent turnEndEvent = (TurnEndEvent) event;
		if (turnEndEvent.getPlayer() != target.getOwner()) {
			return;
		}
		
		event.getGameContext().getLogic().castSpell(turnEndEvent.getPlayer(), spell, target);
		event.getGameContext().getEventManager().removeGameEventListener(this);
	}

}
