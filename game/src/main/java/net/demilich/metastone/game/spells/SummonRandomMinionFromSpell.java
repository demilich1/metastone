package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SummonRandomMinionFromSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Card fromCard = SpellUtils.getCard(context, desc);
		CardCollection allMinions = CardCatalogue.query(context.getDeckFormat(), CardType.MINION);
		CardCollection relevantMinions = new CardCollection();
		for (Card card : allMinions) {
			if (context.getLogic().getModifiedManaCost(player, fromCard) == card.getBaseManaCost()) {
				relevantMinions.add(card);
			}
		}
		
		int boardPosition = SpellUtils.getBoardPosition(context, player, desc, source);
		MinionCard minionCard = (MinionCard) relevantMinions.getRandom();
		context.getLogic().summon(player.getId(), minionCard.summon(), null, boardPosition, false);
	}

}
