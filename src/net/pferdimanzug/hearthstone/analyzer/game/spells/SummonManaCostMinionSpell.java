package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class SummonManaCostMinionSpell extends Spell {
	
	public static SpellDesc create(int manaCost) {
		SpellDesc desc = new SpellDesc(SummonManaCostMinionSpell.class);
		desc.setValue(manaCost);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int manaCost = desc.getValue();
		MinionCard minionCard = getRandomMinionCardWithCost(manaCost);
		context.getLogic().summon(player.getId(), minionCard.summon(), null, null, false);
	}
	
	private static MinionCard getRandomMinionCardWithCost(int manaCost) {
		CardCollection allMinions = CardCatalogue.query(CardType.MINION);
		CardCollection minionWithMatchingCost = new CardCollection();
		for (Card card : allMinions) {
			if (card.getBaseManaCost() == manaCost) {
				minionWithMatchingCost.add(card);
			}
		}
		return (MinionCard) minionWithMatchingCost.getRandom();
	}

}
