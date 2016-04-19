package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public class TransformToRandomMinionSpell extends TransformMinionSpell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(TransformToRandomMinionSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter filter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);

		CardCollection allMinions = CardCatalogue.query(context.getDeckFormat(), CardType.MINION);
		CardCollection filteredMinions = new CardCollection();
		for (Card card : allMinions) {
			MinionCard minionCard = (MinionCard) card;
			if (filter == null || filter.matches(context, player, card)) {
				filteredMinions.add(minionCard);
			}
		}
		MinionCard randomCard = (MinionCard) filteredMinions.getRandom();

		if (randomCard != null) {
			SpellDesc transformMinionSpell = TransformMinionSpell.create(randomCard.getCardId());
			super.onCast(context, player, transformMinionSpell, source, target);
		}
	}

}
