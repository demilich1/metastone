package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class TransformToMinionWithManaCostSpell extends TransformMinionSpell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(TransformToMinionWithManaCostSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion minion = (Minion) target;
		int manaCost = minion.getSourceCard().getBaseManaCost();
		desc.set(SpellArg.CARD, getRandomMinionWithCost(manaCost));
		super.onCast(context, player, desc, target);
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

}
