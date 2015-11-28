package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.Environment;
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
import net.demilich.metastone.game.targeting.EntityReference;

public class DiscoverRandomCardSpell extends Spell {
	
	public static SpellDesc create(EntityReference target, SpellDesc spell1) {
		Map<SpellArg, Object> arguments = SpellDesc.build(MetaSpell.class);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.SPELL_1, spell1);
		return new SpellDesc(arguments);
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		CardCollection cards = CardCatalogue.query((CardType) null, (Rarity) null, HeroClass.ANY);
		HeroClass heroClass = player.getHero().getHeroClass();
		if (!SpellUtils.isBaseClass(heroClass)) {
			Card card = (Card) context.getEnvironment().get(Environment.PENDING_CARD);
			heroClass = card.getClassRestriction();
			if (heroClass == HeroClass.ANY) {
				heroClass = SpellUtils.getRandomHeroClass();
			}
		}
		CardCollection classCards = CardCatalogue.query((CardType) null, (Rarity) null, heroClass);
		for (int i = 0; i < 4; i++) {
			cards.addAll(classCards);
		}
		
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (cardFilter.matches(context, player, card)) {
				result.add(card);
			}
		}
		cards = new CardCollection();
		
		int count = desc.getInt(SpellArg.HOW_MANY, 3);
		for (int i = 0; i < count; i++) {
			if (!result.isEmpty()) {
				Card card = null;
				do {
					card = result.getRandom();
					cards.remove(card);
				} while (cards.contains(card));
				cards.add(card);
			}
		}
		
		SpellUtils.castChildSpell(context, player, SpellUtils.getDiscover(context, player, desc, cards).getSpell(), source, target);
	}

}
