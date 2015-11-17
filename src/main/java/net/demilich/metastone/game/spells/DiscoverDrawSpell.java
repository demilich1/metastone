package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.DiscoverAction;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class DiscoverDrawSpell extends Spell {
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardCollection cards = new CardCollection();
		
		if (player.getDeck().isEmpty()) {
		  return;
		}
		
		int count = desc.getInt(SpellArg.HOW_MANY, 3);
		for (int i = 0; i < count; i++) {
			if (!player.getDeck().isEmpty()) {
				Card card = player.getDeck().removeFirst();
				cards.add(card);
			}
		}
		
		SpellDesc spell = (SpellDesc) desc.get(SpellArg.SPELL_1);
		List<GameAction> discoverActions = new ArrayList<>();
		for (Card card : cards) {
			SpellDesc spellClone = spell.clone();
			spellClone.addArg(SpellArg.CARD, card.getCardId());
			DiscoverAction discover = DiscoverAction.createDiscover(spellClone);
			discover.setActionSuffix(card.getName());
			discoverActions.add(discover);
		}
		
		GameAction discoverAction = player.getBehaviour().requestAction(context, player, discoverActions);
		DiscoverAction discover = (DiscoverAction) discoverAction;
		SpellUtils.castChildSpell(context, player, discover.getSpell(), source, target);
	}

}
