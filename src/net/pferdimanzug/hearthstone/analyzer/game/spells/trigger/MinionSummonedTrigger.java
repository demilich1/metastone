package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

public class MinionSummonedTrigger extends GameEventTrigger {
	
	private final TargetPlayer targetPlayer;

	public MinionSummonedTrigger(TargetPlayer targetPlayer) {
		this.targetPlayer = targetPlayer;
	}
	
	public MinionSummonedTrigger() {
		this(TargetPlayer.BOTH);
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		SummonEvent summonEvent = (SummonEvent) event;
		switch (targetPlayer) {
		case BOTH:
			return true;
		case OPPONENT:
			return summonEvent.getMinion().getOwner() != getOwner();
		case SELF:
			return summonEvent.getMinion().getOwner() == getOwner();
		}
		return false;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.SUMMON;
	}

}
