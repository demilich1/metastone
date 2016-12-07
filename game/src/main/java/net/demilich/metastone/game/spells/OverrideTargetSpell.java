package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class OverrideTargetSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(OverrideTargetSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		if (context.getEnvironment().get(Environment.TARGET_OVERRIDE) == null) {
			context.getEnvironment().put(Environment.TARGET_OVERRIDE, target.getReference());
		}
	}

}
