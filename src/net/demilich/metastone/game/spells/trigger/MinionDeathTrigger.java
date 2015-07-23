package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.KillEvent;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class MinionDeathTrigger extends GameEventTrigger {
	
	public MinionDeathTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean fire(GameEvent event, Entity host) {
		KillEvent killEvent = (KillEvent) event;
		if (killEvent.getVictim().getEntityType() != EntityType.MINION) {
			return false;
		}
		
		Minion minion = (Minion) killEvent.getVictim();
		
		Race race = (Race) desc.get(EventTriggerArg.RACE);
		if (race != null && minion.getRace() != race) {
			return false;
		}
		
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		switch (targetPlayer) {
		case BOTH:
			return true;
		case SELF:
		case OWNER:
			return minion.getOwner() == host.getOwner();
		case OPPONENT:
			return minion.getOwner() != host.getOwner();
		case ACTIVE:
			return minion.getOwner() == event.getGameContext().getActivePlayerId();
		case INACTIVE:
			return minion.getOwner() != event.getGameContext().getActivePlayerId();
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.KILL;
	}

}
