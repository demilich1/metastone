package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SummonRandomMinionFromSpell extends Spell {

	private int getBoardPosition(GameContext context, Player player, SpellDesc desc, Entity source) {
		final int UNDEFINED = -1;
		int boardPosition = desc.getInt(SpellArg.BOARD_POSITION_ABSOLUTE, -1);
		if (boardPosition != UNDEFINED) {
			return boardPosition;
		}
		RelativeToSource relativeBoardPosition = (RelativeToSource) desc.get(SpellArg.BOARD_POSITION_RELATIVE);
		if (relativeBoardPosition == null) {
			return UNDEFINED;
		}

		int sourcePosition = context.getBoardPosition((Minion) source);
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
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Card fromCard = SpellUtils.getCard(context, desc);
		CardCollection allMinions = CardCatalogue.query(CardType.MINION);
		CardCollection relevantMinions = new CardCollection();
		for (Card card : allMinions) {
			if (context.getLogic().getModifiedManaCost(player, fromCard) == card.getBaseManaCost()) {
				relevantMinions.add(card);
			}
		}
		
		int boardPosition = getBoardPosition(context, player, desc, source);
		MinionCard minionCard = (MinionCard) relevantMinions.getRandom();
		context.getLogic().summon(player.getId(), minionCard.summon(), null, boardPosition, false);
	}

}
