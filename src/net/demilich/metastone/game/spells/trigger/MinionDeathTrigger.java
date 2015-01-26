package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.KillEvent;
import net.demilich.metastone.game.spells.TargetPlayer;

public class MinionDeathTrigger extends GameEventTrigger {
	
	private TargetPlayer targetPlayer;
	private Race race;

	public MinionDeathTrigger() {
		this(TargetPlayer.BOTH, null);
	}
	
	public MinionDeathTrigger(TargetPlayer targetPlayer) {
		this(targetPlayer, null);
	}
	
	public MinionDeathTrigger(TargetPlayer targetPlayer, Race race) {
		this.targetPlayer = targetPlayer;
		this.race = race;
	}
	
	@Override
	public boolean fire(GameEvent event, Entity host) {
		KillEvent killEvent = (KillEvent) event;
		if (killEvent.getVictim().getEntityType() != EntityType.MINION) {
			return false;
		}
		
		Minion minion = (Minion) killEvent.getVictim();
		
		if (race != null && minion.getRace() != race) {
			return false;
		}
		
		switch (targetPlayer) {
		case BOTH:
			return true;
		case SELF:
			return minion.getOwner() == host.getOwner();
		case OPPONENT:
			return minion.getOwner() != host.getOwner();
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.KILL;
	}

}
