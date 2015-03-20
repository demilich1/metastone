package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.ReturnMinionToHandSpell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class AlarmOBotSpell extends ReturnMinionToHandSpell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(AlarmOBotSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.SELF);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		// return Alarm-o-bot to hand
		super.onCast(context, player, desc, source, target);
		if (!player.getHand().hasCardOfType(CardType.MINION)) {
			return;
		}
		// summon a random minion and remove the corresponding card
		MinionCard randomMinionCard = (MinionCard) player.getHand().getRandomOfType(CardType.MINION);
		context.getLogic().removeCard(player.getId(), randomMinionCard);
		context.getLogic().summon(player.getId(), randomMinionCard.summon());
	}

}
