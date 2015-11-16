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
		CardCollection result = new CardCollection();
		for (Card card : SpellUtils.getCards(desc)) {
			result.add(card);
		}
		
		CardCollection cards = new CardCollection();
		
		int count = desc.getInt(SpellArg.HOW_MANY, 3);
		for (int i = 0; i < count; i++) {
			if (!result.isEmpty()) {
				Card card = result.getRandom();
				cards.add(card);
				result.remove(card);
			}
		}
		
		SpellDesc spell = (SpellDesc) desc.get(SpellArg.SPELL_1);
		List<GameAction> discoverActions = new ArrayList<>();
		List<SpellDesc> spells = New ArrayList<SpellDesc>();
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
