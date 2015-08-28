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
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class TransformToMinionWithManaCostSpell extends TransformMinionSpell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(TransformToMinionWithManaCostSpell.class);
		return new SpellDesc(arguments);
	}

	private static MinionCard getRandomMinionWithCost(int manaCost) {
		CardCollection allMinions = CardCatalogue.query(CardType.MINION);
		CardCollection minionsWithSameCost = new CardCollection();
		for (Card card : allMinions) {
			MinionCard minionCard = (MinionCard) card;
			if (minionCard.getBaseManaCost() == manaCost) {
				minionsWithSameCost.add(minionCard);
			}
		}
		return (MinionCard) minionsWithSameCost.getRandom();
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Minion minion = (Minion) target;
		int manaCost = minion.getSourceCard().getBaseManaCost();
		Card randomCard = getRandomMinionWithCost(manaCost);
		SpellDesc transformMinionSpell = TransformMinionSpell.create(randomCard.getCardId());
		super.onCast(context, player, transformMinionSpell, source, target);
	}

}
