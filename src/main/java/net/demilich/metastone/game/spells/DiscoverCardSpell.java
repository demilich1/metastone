package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.DiscoverAction;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.targeting.EntityReference;

public class DiscoverCardSpell extends Spell {
	
	public static SpellDesc create(EntityReference target, SpellDesc spell1) {
		Map<SpellArg, Object> arguments = SpellDesc.build(MetaSpell.class);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.SPELL_1, spell1);
		return new SpellDesc(arguments);
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		CardCollection cards = CardCatalogue.query((CardType) null);
		CardCollection result = new CardCollection();
		String replacementCard = (String) desc.get(SpellArg.CARD);
		for (Card card : cards) {
			if (cardFilter.matches(context, player, card)) {
				result.add(card);
			}
		}
		cards = new CardCollection();
		Card card = null;
		
		int count = desc.getInt(SpellArg.HOW_MANY, 1);
		for (int i = 0; i < count; i++) {
			if (!result.isEmpty()) {
				card = result.getRandom();
				cards.add(card);
				result.remove(card);
			} else if (replacementCard != null) {
				card = CardCatalogue.getCardById(replacementCard);
			}
		}
		SpellDesc spell = (SpellDesc) desc.get(SpellArg.SPELL_1);
		SpellDesc spell2 = spell.clone();
		
		spell.addArg(SpellArg.CARD, card.getCardId());
		DiscoverAction discover = DiscoverAction.createDiscover(spell);
		discover.setActionSuffix(card.getName());
		
		card = result.getRandom();
		spell2.addArg(SpellArg.CARD, card.getCardId());
		DiscoverAction discover2 = DiscoverAction.createDiscover(spell2);
		discover2.setActionSuffix(card.getName());
		
		GameAction discoverAction = (GameAction) discover;
		
		List<GameAction> discoverActions = new ArrayList<>();
		discoverActions.add(discover);
		discoverActions.add(discover2);

		
		discoverAction = player.getBehaviour().requestAction(context, player, discoverActions);
		discover = (DiscoverAction) discoverAction;
		SpellUtils.castChildSpell(context, player, discover.getSpell(), source, target);
	}

}
