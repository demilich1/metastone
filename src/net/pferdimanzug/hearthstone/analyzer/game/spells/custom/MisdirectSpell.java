package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MisdirectSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(MisdirectSpell.class);
		desc.setTarget(EntityReference.EVENT_TARGET);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Entity attacker = (Entity) context.getEnvironment().get(Environment.ATTACKER);
		List<Entity> validTargets = context.resolveTarget(player, null, EntityReference.ALL_CHARACTERS);
		// misdirection cannot redirect to attacker
		validTargets.remove(attacker);
		// misdirection cannot redirect to original target
		validTargets.remove(target);

		Entity randomTarget = SpellUtils.getRandomTarget(validTargets);
		context.getEnvironment().put(Environment.TARGET_OVERRIDE, randomTarget);
	}
}
