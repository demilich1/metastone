package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
