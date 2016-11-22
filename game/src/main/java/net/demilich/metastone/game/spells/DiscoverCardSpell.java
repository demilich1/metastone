package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DiscoverCardSpell extends Spell {
	
	public static SpellDesc create(EntityReference target, SpellDesc spell) {
		Map<SpellArg, Object> arguments = SpellDesc.build(DiscoverCardSpell.class);
		arguments.put(SpellArg.TARGET, target);
		arguments.put(SpellArg.SPELL, spell);  
		return new SpellDesc(arguments);
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardCollection result = new CardCollection();
		boolean cannotReceiveOwned = desc.getBool(SpellArg.CANNOT_RECEIVE_OWNED);
		for (Card card : SpellUtils.getCards(context, desc)) {
			if (!cannotReceiveOwned || !context.getLogic().hasCard(player, card)) {
				result.add(card);
			}
		}
		
		
		CardCollection cards = new CardCollection();
		
		int count = desc.getValue(SpellArg.HOW_MANY, context, player, target, source, 3);
		for (int i = 0; i < count; i++) {
			if (!result.isEmpty()) {
				Card card = result.getRandom();
				cards.add(card);
				result.remove(card);
			}
		}
		
		if (!cards.isEmpty()) {
			SpellUtils.castChildSpell(context, player, SpellUtils.getDiscover(context, player, desc, cards).getSpell(), source, target);
		}
	}

}
