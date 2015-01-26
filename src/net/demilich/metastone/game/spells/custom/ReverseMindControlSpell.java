package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.MindControlSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ReverseMindControlSpell extends MindControlSpell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(ReverseMindControlSpell.class);
		return desc;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Player opponent = context.getOpponent(player);
		super.onCast(context, opponent, desc, target);
	}
}

