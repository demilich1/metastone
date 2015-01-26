package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class DestroySpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(DestroySpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		context.getLogic().markAsDestroyed((Actor) target);
	}

}
