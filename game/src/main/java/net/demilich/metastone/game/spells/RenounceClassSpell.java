package net.demilich.metastone.game.spells;

import java.util.Map;

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
import net.demilich.metastone.game.spells.desc.filter.CardFilter;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.spells.desc.filter.FilterArg;
import net.demilich.metastone.game.spells.desc.filter.FilterDesc;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;
import net.demilich.metastone.game.spells.desc.trigger.TriggerDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.AlgebraicOperation;
import net.demilich.metastone.game.spells.trigger.CardDrawnTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class RenounceClassSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		
		HeroClass renouncedClass = (HeroClass) cardFilter.getArg(FilterArg.HERO_CLASS);
		HeroClass rebornClass = SpellUtils.getRandomHeroClassExcept(renouncedClass);
		CardCollection cards = CardCatalogue.query(context.getDeckFormat());
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (card.getHeroClass() == rebornClass) {
				result.add(card);
			}
		}
		
		int manaCostModifier = desc.getValue(SpellArg.MANA_MODIFIER, context, player, target, source, 0);
		
		CardCollection heroPowers = CardCatalogue.getHeroPowers(context.getDeckFormat());
		for (Card heroPowerCard : heroPowers) {
			if (heroPowerCard.getHeroClass() == rebornClass) {
				HeroPower heroPower = (HeroPower) heroPowerCard;
				player.getHero().setHeroPower(heroPower);
			}
		}

		CardCollection replacedCards = new CardCollection();
		for (Card card : player.getDeck()) {
			if (card.getHeroClass() == renouncedClass) {
				replacedCards.add(card);
			}
		}
		for (Card card : replacedCards) {
			Card replacement = result.getRandom().getCopy();
			context.getLogic().replaceCardInDeck(player.getId(), card, replacement);
		}
		Map<EventTriggerArg, Object> eventTriggerMap = EventTriggerDesc.build(CardDrawnTrigger.class);
		eventTriggerMap.put(EventTriggerArg.TARGET_PLAYER, TargetPlayer.SELF);
		Map<FilterArg, Object> filterMap = FilterDesc.build(CardFilter.class);
		filterMap.put(FilterArg.HERO_CLASS, rebornClass);
		CardFilter newCardFilter = new CardFilter(new FilterDesc(filterMap));
		TriggerDesc triggerDesc = new TriggerDesc();
		triggerDesc.eventTrigger = new EventTriggerDesc(eventTriggerMap);
		triggerDesc.spell = CardCostModifierSpell.create(EntityReference.EVENT_TARGET, AlgebraicOperation.ADD, manaCostModifier, newCardFilter);
		SpellDesc spellTriggerSpell = AddSpellTriggerSpell.create(triggerDesc);
		SpellUtils.castChildSpell(context, player, spellTriggerSpell, source, player);
		
		replacedCards = new CardCollection();
		for (Card card : player.getHand()) {
			if (card.getHeroClass() == renouncedClass) {
				replacedCards.add(card);
			}
		}
		for (Card card : replacedCards) {
			Card replacement = result.getRandom().getCopy();
			context.getLogic().replaceCard(player.getId(), card, replacement);
			SpellUtils.castChildSpell(context, player, CardCostModifierSpell.create(replacement.getReference(), AlgebraicOperation.ADD, manaCostModifier), source, null);
		}
	}

}
