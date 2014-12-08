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
		int boardPosition = desc.contains(SpellArg.BOARD_POSITION) ? desc.getInt(SpellArg.BOARD_POSITION) : -1;
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case BOTH:
			summon(context, player, minionCards, boardPosition);
			summon(context, opponent, minionCards, boardPosition);
			break;
		case OPPONENT:
			summon(context, opponent, minionCards, boardPosition);
			break;
		case SELF:
			summon(context, player, minionCards, boardPosition);
			break;
		}
	}

	private void summon(GameContext context, Player player, MinionCard[] minionCards, int boardPosition) {
		for (MinionCard minionCard : minionCards) {
			context.getLogic().summon(player.getId(), minionCard.summon(), null, boardPosition, false);
		}
	}

}
