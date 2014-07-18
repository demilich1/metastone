package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.KillEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

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
