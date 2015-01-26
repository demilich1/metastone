package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class AlarmOBotSpell extends ReturnMinionToHandSpell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(AlarmOBotSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		// return Alarm-o-bot to hand
		super.onCast(context, player, desc, target);
		if (!player.getHand().hasCardOfType(CardType.MINION)) {
			return;
		}
		// summon a random minion and remove the corresponding card
		MinionCard randomMinionCard = (MinionCard) player.getHand().getRandomOfType(CardType.MINION);
		context.getLogic().removeCard(player.getId(), randomMinionCard);
		context.getLogic().summon(player.getId(), randomMinionCard.summon());
	}

}
