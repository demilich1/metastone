package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.events.SummonEvent;
import net.demilich.metastone.game.spells.TargetPlayer;

public class MinionSummonedTrigger extends GameEventTrigger {

	private final TargetPlayer targetPlayer;
	private final Race race;

	public MinionSummonedTrigger() {
		this(TargetPlayer.BOTH);
	}

	public MinionSummonedTrigger(TargetPlayer targetPlayer) {
		this(targetPlayer, null);
	}

	public MinionSummonedTrigger(TargetPlayer targetPlayer, Race race) {
		this.targetPlayer = targetPlayer;
		this.race = race;
	}

	@Override
	public boolean fire(GameEvent event, Entity host) {
		SummonEvent summonEvent = (SummonEvent) event;
		if (race != null && summonEvent.getMinion().getRace() != race) {
			return false;
		}
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
