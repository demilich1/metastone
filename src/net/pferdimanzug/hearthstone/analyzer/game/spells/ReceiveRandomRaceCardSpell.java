package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
			Card randomRaceCard = raceCards.getRandom();
			context.getLogic().receiveCard(player.getId(), randomRaceCard);	
		}
	}

}
