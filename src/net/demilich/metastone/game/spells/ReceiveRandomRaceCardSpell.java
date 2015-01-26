package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ReceiveRandomRaceCardSpell extends Spell {

	public static SpellDesc create(Race race, int count) {
		SpellDesc desc = new SpellDesc(ReceiveRandomRaceCardSpell.class);
		desc.set(SpellArg.RACE, race);
		desc.setValue(count);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		CardCollection minionCards = CardCatalogue.query(CardType.MINION, null, null);
		Race race = (Race) desc.get(SpellArg.RACE);
		CardCollection raceCards = SpellUtils.getCards(minionCards, card -> card.getTag(GameTag.RACE) == race);

		int count = desc.getValue();
		for (int i = 0; i < count; i++) {
			Card randomRaceCard = raceCards.getRandom().clone();
			context.getLogic().receiveCard(player.getId(), randomRaceCard);	
		}
	}

}
