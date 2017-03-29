package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.ShuffleMinionToDeckSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class MadamGoyaSpell extends ShuffleMinionToDeckSpell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		// Check to see if there is a minion before returning to deck!
		if (!player.getDeck().hasCardOfType(CardType.MINION) || context.getLogic().canSummonMoreMinions(player)) {
			return;
		}

		// Summon a random minion and remove the corresponding card
		// before adding the target to your deck!
		MinionCard randomMinionCard = (MinionCard) player.getDeck().getRandomOfType(CardType.MINION);
		context.getLogic().removeCardFromDeck(player.getId(), randomMinionCard);
		// return target to deck (Now it's safe and won't destroy itself!)
		
		// Summon the minion, which ALSO won't destroy itself...
		context.getLogic().summon(player.getId(), randomMinionCard.summon());
		
		// If there is no minion to shuffle... Idk, blame a wizard.
		super.onCast(context, player, desc, source, target);
	}

}
