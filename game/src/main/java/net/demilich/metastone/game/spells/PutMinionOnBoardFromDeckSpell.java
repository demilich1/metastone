package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.CardLocation;

public class PutMinionOnBoardFromDeckSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		MinionCard minionCard = (MinionCard) target;

		if (minionCard.getLocation() == CardLocation.DECK) {
			context.getLogic().summon(player.getId(), minionCard.summon());
			context.getLogic().removeCardFromDeck(player.getId(), minionCard);
		}
	}

}
