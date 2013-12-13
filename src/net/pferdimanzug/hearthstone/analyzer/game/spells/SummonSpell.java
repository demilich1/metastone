package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SummonSpell implements ISpell {
	
	private final MinionCard minionCard;
	
	public SummonSpell(MinionCard minionCard) {
		this.minionCard = minionCard;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		context.getLogic().summon(player, minionCard.summon(), null);
	}

}
