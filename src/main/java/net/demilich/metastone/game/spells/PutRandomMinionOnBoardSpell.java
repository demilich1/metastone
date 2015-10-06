package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.CardLocation;

public class PutRandomMinionOnBoardSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Race race = (Race) desc.get(SpellArg.RACE);
		CardLocation cardLocation = (CardLocation) desc.get(SpellArg.CARD_LOCATION);
		putRandomMinionFromDeckOnBoard(context, player, race, cardLocation);
	}

	private void putRandomMinionFromDeckOnBoard(GameContext context, Player player, Race race, CardLocation cardLocation) {
		MinionCard minionCard = null;
		CardCollection collection = cardLocation == CardLocation.HAND ? player.getHand() : player.getDeck();
		if (race == null) {
			minionCard = (MinionCard) collection.getRandomOfType(CardType.MINION);
		} else {
			minionCard = (MinionCard) SpellUtils.getRandomCard(collection, card -> card.getAttribute(Attribute.RACE) == race);
		}

		if (minionCard == null) {
			return;
		}

		context.getLogic().summon(player.getId(), minionCard.summon());
		context.getLogic().removeCard(player.getId(), minionCard);
	}

}
