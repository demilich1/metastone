package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class MindControlSpell extends Spell {
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(MindControlSpell.class);
		return desc;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion minion = (Minion) target;
		context.getLogic().mindControl(player, minion);
	}

}
