package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SpellUtils;

public class CardFilter extends EntityFilter {

	public CardFilter(FilterDesc desc) {
		super(desc);
	}

	@Override
	protected boolean test(GameContext context, Player player, Entity entity) {
		Card card = null;
		if (entity instanceof Card) {
			card = (Card) entity;
		} else if (entity instanceof Actor) {
			Actor actor = (Actor) entity;
			card = actor.getSourceCard();
		} else {
			return false;
		}

		CardType cardType = (CardType) desc.get(FilterArg.CARD_TYPE);
		if (cardType != null && !card.getCardType().isCardType(cardType)) {
			return false;
		}
		Race race = (Race) desc.get(FilterArg.RACE);
		if (race != null && race != card.getAttribute(Attribute.RACE)) {
			return false;
		}
		
		HeroClass heroClass = (HeroClass) desc.get(FilterArg.HERO_CLASS);
		if (heroClass == HeroClass.OPPONENT) {
			heroClass = context.getOpponent(player).getHero().getHeroClass();
		}
		if (heroClass != null && heroClass != HeroClass.ANY && heroClass != card.getClassRestriction()) {
			return false;
		}
		
		if (desc.contains(FilterArg.MANA_COST)) {
			int manaCost = desc.getValue(FilterArg.MANA_COST, context, player, null, null, 0);
			if (manaCost != card.getBaseManaCost()) {
				return false;
			}
		}
		Rarity rarity = (Rarity) desc.get(FilterArg.RARITY);
		if (rarity != null && card.getRarity().isRarity(rarity)) {
			return false;
		}
		
		if (desc.contains(FilterArg.ATTRIBUTE) && desc.contains(FilterArg.OPERATION)) {
			Attribute attribute = (Attribute) desc.get(FilterArg.ATTRIBUTE);
			Operation operation = (Operation) desc.get(FilterArg.OPERATION);
			if (operation == Operation.HAS) {
				return card.hasAttribute(attribute);
			}
	
			int targetValue = desc.getInt(FilterArg.VALUE);
			int actualValue = card.getAttributeValue(attribute);
	
			return SpellUtils.evaluateOperation(operation, actualValue, targetValue);
		}

		return true;
	}

}
