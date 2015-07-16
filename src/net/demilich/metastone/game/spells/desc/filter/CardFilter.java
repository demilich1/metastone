package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;

public class CardFilter extends EntityFilter {

	public CardFilter(FilterDesc desc) {
		super(desc);
	}

	@Override
	protected boolean test(Entity entity, FilterDesc desc) {
		if (!(entity instanceof Card)) {
			return false;
		}
		Card card = (Card) entity;
		CardType cardType = (CardType) desc.get(FilterArg.CARD_TYPE);
		if (cardType != null && cardType != card.getCardType()) {
			return false;
		}
		Race race = (Race) desc.get(FilterArg.RACE);
		if (race != null && race != card.getTag(GameTag.RACE)) {
			return false;
		}
		if (desc.contains(FilterArg.MANA_COST)) {
			int manaCost = desc.getInt(FilterArg.MANA_COST);
			if (manaCost != card.getBaseManaCost()) {
				return false;
			}
		}
		Rarity rarity = (Rarity) desc.get(FilterArg.RARITY);
		if (rarity != null && card.getRarity() != rarity) {
			return false;
		}
		return true;
	}

}
