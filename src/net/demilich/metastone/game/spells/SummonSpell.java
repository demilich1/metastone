package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class SummonSpell extends Spell {
	
	public static SpellDesc create(MinionCard... minionCards) {
		return create(null, minionCards);
	}

	public static SpellDesc create(RelativeToSource relativeBoardPosition, MinionCard... minionCards) {
		SpellDesc desc = new SpellDesc(SummonSpell.class);
		desc.set(SpellArg.CARDS, minionCards);
		desc.setTarget(EntityReference.NONE);
		if (relativeBoardPosition != null) {
			desc.set(SpellArg.BOARD_POSITION_RELATIVE, relativeBoardPosition);
		}
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		MinionCard[] minionCards = (MinionCard[]) desc.get(SpellArg.CARDS);
		int boardPosition = getBoardPosition(context, player, desc);
		for (MinionCard minionCard : minionCards) {
			context.getLogic().summon(player.getId(), minionCard.summon(), null, boardPosition, false);
		}
	}
	
	private int getBoardPosition(GameContext context, Player player, SpellDesc desc) {
		final int UNDEFINED = -1;
		int boardPosition = desc.contains(SpellArg.BOARD_POSITION_ABSOLUTE) ? desc.getInt(SpellArg.BOARD_POSITION_ABSOLUTE) : -1;
		if (boardPosition != UNDEFINED) {
			return boardPosition;
		}
		RelativeToSource relativeBoardPosition = (RelativeToSource) desc.get(SpellArg.BOARD_POSITION_RELATIVE);
		if (relativeBoardPosition == null) {
			return UNDEFINED;
		}
		Minion sourceMinion = (Minion) context.resolveSingleTarget(desc.getSourceEntity());
		
		int sourcePosition = context.getBoardPosition(sourceMinion);
		if (sourcePosition == UNDEFINED) {
			return UNDEFINED;
		}
		switch (relativeBoardPosition) {
		case LEFT:
			return sourcePosition;
		case RIGHT:
			return sourcePosition + 1;
		default:
			return UNDEFINED;
		}
	}

}
