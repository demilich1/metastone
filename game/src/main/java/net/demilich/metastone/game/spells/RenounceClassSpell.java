package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.heroes.powers.HeroPower;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.spells.desc.filter.FilterArg;

public class RenounceClassSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		
		HeroClass renouncedClass = (HeroClass) cardFilter.getArg(FilterArg.HERO_CLASS);
		HeroClass rebornClass = SpellUtils.getRandomHeroClassExcept(renouncedClass);
		CardCollection cards = CardCatalogue.query(context.getDeckFormat());
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (card.getClassRestriction() == rebornClass) {
				result.add(card);
			}
		}
		
		int manaCostModifier = desc.getValue(SpellArg.MANA_MODIFIER, context, player, target, source, 0);
		
		CardCollection heroPowers = CardCatalogue.getHeroPowers(context.getDeckFormat());
		
		for (Card heroPowerCard : heroPowers) {
			if (heroPowerCard.getClassRestriction() == rebornClass) {
				HeroPower heroPower = (HeroPower) heroPowerCard;
				player.getHero().setHeroPower(heroPower);
			}
		}

		CardCollection replacedCards = new CardCollection();
		for (Card card : player.getDeck()) {
			if (card.getClassRestriction() == renouncedClass) {
				replacedCards.add(card);
				
			}
		}
		for (Card card : replacedCards) {
			Card replacement = result.getRandom().getCopy();
			replacement.setAttribute(Attribute.MANA_COST_MODIFIER, manaCostModifier);
			context.getLogic().replaceCardInDeck(player.getId(), card, replacement);
		}
		
		for (Card card : player.getHand()) {
			if (card.getClassRestriction() == renouncedClass) {
				replacedCards.add(card);
			}
		}
		for (Card card : replacedCards) {
			Card replacement = result.getRandom().getCopy();
			replacement.setAttribute(Attribute.MANA_COST_MODIFIER, manaCostModifier);
			context.getLogic().replaceCard(player.getId(), card, replacement);
		}
	}

}
