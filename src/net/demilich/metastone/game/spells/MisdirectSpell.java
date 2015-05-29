package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class MisdirectSpell extends Spell {
	
	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(MisdirectSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.EVENT_TARGET);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Actor attacker = (Actor) context.getEnvironment().get(Environment.ATTACKER);
		Actor randomTarget = context.getLogic().getAnotherRandomTarget(player, attacker, (Actor) target, EntityReference.ALL_CHARACTERS);
		context.getEnvironment().put(Environment.TARGET_OVERRIDE, randomTarget);
	}
}
