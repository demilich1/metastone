package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.CardLocation;
import net.demilich.metastone.game.targeting.EntityReference;

public class PutRandomMinionOnBoardSpell extends Spell {

	public static SpellDesc create(Race race, CardLocation cardLocation) {
		SpellDesc desc = new SpellDesc(PutRandomMinionOnBoardSpell.class);
		if (race != null) {
			desc.set(SpellArg.RACE, race);
		}
		desc.set(SpellArg.CARD_LOCATION, cardLocation);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Race race = (Race) desc.get(SpellArg.RACE);
		EntityReference source = desc.getSourceEntity();
		CardLocation cardLocation = (CardLocation) desc.get(SpellArg.CARD_LOCATION);
		putRandomMinionFromDeckOnBoard(context, player, race, source, cardLocation);
	}

	private void putRandomMinionFromDeckOnBoard(GameContext context, Player player, Race race, EntityReference source, CardLocation cardLocation) {
		MinionCard minionCard = null;
		CardCollection collection = cardLocation == CardLocation.HAND ? player.getHand() : player.getDeck();
		if (race == null) {
			minionCard = (MinionCard) collection.getRandomOfType(CardType.MINION);
		} else {
			minionCard = (MinionCard) SpellUtils.getRandomCard(collection, card -> card.getTag(GameTag.RACE) == race);
		}

		if (minionCard == null) {
			return;
		}
		SpellDesc summonSpell = SummonSpell.create(minionCard);
		summonSpell.setSourceEntity(source);
		context.getLogic().castSpell(player.getId(), summonSpell);
		context.getLogic().removeCard(player.getId(), minionCard);
	}

}
