package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class CopySpellCardSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(CopySpellCardSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Card targetCard = (Card) target;
		Player owner = context.getPlayer(targetCard.getOwner());
		Player opponent = context.getOpponent(owner);
		Card copy = targetCard.clone();
		context.getLogic().receiveCard(opponent.getId(), copy);
	}
}
