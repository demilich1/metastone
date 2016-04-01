package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.spells.desc.filter.FilterArg;
import net.demilich.metastone.game.targeting.EntityReference;

public class DiscoverRandomCardSpell extends Spell {
	
	public static SpellDesc create(EntityReference target, SpellDesc spell) {
		Map<SpellArg, Object> arguments = SpellDesc.build(DiscoverRandomCardSpell.class);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.SPELL, spell);
		return new SpellDesc(arguments);
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		CardCollection cards = CardCatalogue.query(context.getDeckFormat(), (CardType) null, (Rarity) null, HeroClass.ANY);
		HeroClass heroClass = (HeroClass) cardFilter.getArg(FilterArg.HERO_CLASS);
		if (heroClass == null) {
			heroClass = player.getHero().getHeroClass();
		}
		if (heroClass == HeroClass.OPPONENT) {
			heroClass = context.getOpponent(player).getHero().getHeroClass();
		}
		if (heroClass != HeroClass.ANY && !heroClass.isBaseClass()) {
			Card card = context.getPendingCard();
			heroClass = card.getClassRestriction();
			if (heroClass == HeroClass.ANY) {
				heroClass = SpellUtils.getRandomHeroClass();
			}
		}
		if (heroClass == HeroClass.ANY) {
			CardCollection classCards = CardCatalogue.query(context.getDeckFormat());
			cards.addAll(classCards);
		} else {
			CardCollection classCards = CardCatalogue.query(context.getDeckFormat(), heroClass);
			for (int i = 0; i < 4; i++) {
				cards.addAll(classCards);
			}
		}
		
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (cardFilter.matches(context, player, card)) {
				result.add(card);
			}
		}
		cards = new CardCollection();
		
		int count = desc.getValue(SpellArg.HOW_MANY, context, player, target, source, 3);
		for (int i = 0; i < count; i++) {
			if (!result.isEmpty()) {
				Card card = null;
				do {
					card = result.getRandom();
					result.remove(card);
				} while (cards.containsCard(card));
				if (card != null) {
					cards.add(card);
				}
			}
		}
		
		if (!cards.isEmpty()) {
			SpellUtils.castChildSpell(context, player, SpellUtils.getDiscover(context, player, desc, cards).getSpell(), source, target);
		}
	}

}
