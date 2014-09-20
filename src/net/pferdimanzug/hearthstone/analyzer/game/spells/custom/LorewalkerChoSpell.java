package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class LorewalkerChoSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(LorewalkerChoSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Card targetCard = (Card) target;
		Player opponent = context.getOpponent(player);
		Card copy = targetCard.clone();
		context.getLogic().receiveCard(opponent.getId(), copy);
	}
}
