package net.demilich.metastone.game.spells;

import java.util.Map;

import co.paralleluniverse.fibers.Suspendable;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class SummonOneOneCopySpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(SummonOneOneCopySpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	@Suspendable
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		int boardPosition = SpellUtils.getBoardPosition(context, player, desc, source);
		MinionCard minionCard = null;
		if (cardFilter != null) {
			CardCollection relevantMinions = null;
			CardCollection allMinions = CardCatalogue.query(context.getDeckFormat(), CardType.MINION);
			relevantMinions = new CardCollection();
			for (Card card : allMinions) {
				if (cardFilter.matches(context, player, card)) {
					relevantMinions.add(card);
				}
			}
			minionCard = (MinionCard) relevantMinions.getRandom();
		} else {
			minionCard = (MinionCard) ((Minion) target).getSourceCard();
		}

		if (minionCard != null) {
			Minion minion = minionCard.summon();
			if (context.getLogic().summon(player.getId(), minion, null, boardPosition, false)) {
				minion.setAttack(1);
				minion.setHp(1);
				minion.setMaxHp(1);
			}
		}
	}

}