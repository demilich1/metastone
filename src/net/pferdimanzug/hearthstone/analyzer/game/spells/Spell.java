package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public abstract class Spell {

	public void cast(GameContext context, Player player, SpellDesc desc, List<Entity> targets) {
		if (targets == null) {
			onCast(context, player, desc, null);
			return;
		}
		for (Entity target : targets) {
			onCast(context, player, desc, target);
		}
	}

	protected abstract void onCast(GameContext context, Player player, SpellDesc desc, Entity target);

	@Override
	public String toString() {
		return "[SPELL " + getClass().getSimpleName() + "]";
	}

}
