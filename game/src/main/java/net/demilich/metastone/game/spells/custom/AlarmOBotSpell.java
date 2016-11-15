package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class AlarmOBotSpell extends ReturnMinionToHandSpell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		// Check to see if there is a minion before returning to hand!
		// If there is no minion, do not activate!
		if (!player.getHand().hasCardOfType(CardType.MINION)) {
			return;
		}
		// Summon a random minion and remove the corresponding card
		// before adding Alarm-o-bot to your hand!
		MinionCard randomMinionCard = (MinionCard) player.getHand().getRandomOfType(CardType.MINION);
		context.getLogic().removeCard(player.getId(), randomMinionCard);
		// return Alarm-o-bot to hand (Now it's safe and won't destroy itself!)
		super.onCast(context, player, desc, source, target);
		// Summon the minion, which ALSO won't destroy itself...
		context.getLogic().summon(player.getId(), randomMinionCard.summon());
	}

}
