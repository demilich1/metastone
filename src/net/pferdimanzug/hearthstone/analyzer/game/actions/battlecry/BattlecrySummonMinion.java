package net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;

public class BattlecrySummonMinion extends Battlecry {
	
	private MinionCard[] minionCards;

	public BattlecrySummonMinion(MinionCard... minionCards) {
		this.minionCards = minionCards;
	}

	@Override
	public void execute(GameContext context, Player player) {
		for (MinionCard minionCard : minionCards) {
			context.getLogic().summon(player, minionCard.summon(), null);
		}
	}

}
