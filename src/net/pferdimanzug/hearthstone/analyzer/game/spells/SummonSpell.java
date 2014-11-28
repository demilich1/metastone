package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class SummonSpell extends Spell {

	public static SpellDesc create(MinionCard... minionCards) {
		return create(TargetPlayer.SELF, minionCards);
	}

	public static SpellDesc create(TargetPlayer targetPlayer, MinionCard... minionCards) {
		SpellDesc desc = new SpellDesc(SummonSpell.class);
		desc.set(SpellArg.CARDS, minionCards);
		desc.setTargetPlayer(targetPlayer);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		MinionCard[] minionCards = (MinionCard[]) desc.get(SpellArg.CARDS);
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case BOTH:
			summon(context, player.getId(), minionCards);
			summon(context, opponent.getId(), minionCards);
			break;
		case OPPONENT:
			summon(context, opponent.getId(), minionCards);
			break;
		case SELF:
			summon(context, player.getId(), minionCards);
			break;
		}
	}

	private void summon(GameContext context, int playerId, MinionCard[] minionCards) {
		for (MinionCard minionCard : minionCards) {
			context.getLogic().summon(playerId, minionCard.summon());
		}
	}

}
