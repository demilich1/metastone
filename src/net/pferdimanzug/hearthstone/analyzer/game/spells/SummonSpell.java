package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SummonSpell extends Spell {
	
	private final MinionCard[] minionCards;
	
	public SummonSpell(MinionCard... minionCards) {
		this.minionCards = minionCards;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		for (MinionCard minionCard : minionCards) {
			context.getLogic().summon(player.getId(), minionCard.summon(), null, null);
		}
	}

}
