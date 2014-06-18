package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SummonRandomSpell extends Spell {
	
	private final MinionCard[] minions;

	public SummonRandomSpell(MinionCard... minions) {
		this.minions = minions;
	}

	private MinionCard getRandomMinion() {
		int randomIndex = ThreadLocalRandom.current().nextInt(minions.length);
		return minions[randomIndex];
	}
	
	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		MinionCard randomMinionCard = getRandomMinion();
		context.getLogic().summon(player.getId(), randomMinionCard.summon(), null, null);
	}

}
