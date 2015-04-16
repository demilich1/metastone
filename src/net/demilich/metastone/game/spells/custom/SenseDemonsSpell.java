package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SenseDemonsSpell extends Spell {
	
	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(SenseDemonsSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardCollection demonCards = SpellUtils.getCards(player.getDeck(), card -> card.getTag(GameTag.RACE) == Race.DEMON);
		for (int i = 0; i < 2; i++) {
			Card demonCard = null;
			if (demonCards.isEmpty()) {
				//demonCard = new WorthlessImp();

			} else {
				demonCard = demonCards.getRandom();
				demonCards.remove(demonCard);
				player.getDeck().remove(demonCard);
			}
			context.getLogic().receiveCard(player.getId(), demonCard);
		}
	}
}