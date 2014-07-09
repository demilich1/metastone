package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SummonSpell extends Spell {
	
	private final MinionCard[] minionCards;
	private final TargetPlayer targetPlayer;
	
	public SummonSpell(MinionCard... minionCards) {
		this(TargetPlayer.SELF, minionCards);
	}
	
	public SummonSpell(TargetPlayer targetPlayer, MinionCard... minionCards) {
		this.targetPlayer = targetPlayer;
		this.minionCards = minionCards;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case BOTH:
			summon(context, player);
			summon(context, opponent);
			break;
		case OPPONENT:
			summon(context, opponent);
			break;
		case SELF:
			summon(context, player);
			break;
		}
	}
	
	private void summon(GameContext context, Player player) {
		for (MinionCard minionCard : minionCards) {
			context.getLogic().summon(player.getId(), minionCard.summon(), null, null, false);
		}
	}

}
