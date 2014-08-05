package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SummonRandomSpell extends Spell {
	
	public SummonRandomSpell(MinionCard... minions) {
		setCloneableData(minions);
	}

	public List<MinionCard> getMinions() {
		return getCloneableDataCollection();
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		List<MinionCard> minionCards = getMinions();
		MinionCard randomMinionCard = minionCards.get(context.getLogic().random(minionCards.size()));
		context.getLogic().summon(player.getId(), randomMinionCard.summon(), null, null, false);
	}

}
