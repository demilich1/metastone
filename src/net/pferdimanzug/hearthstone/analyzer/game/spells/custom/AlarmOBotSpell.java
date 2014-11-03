package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;

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
		player.getHand().remove(randomMinionCard);
		randomMinionCard.setLocation(CardLocation.VOID);
		context.getLogic().summon(player.getId(), randomMinionCard.summon(), null, null, false);
	}

}
