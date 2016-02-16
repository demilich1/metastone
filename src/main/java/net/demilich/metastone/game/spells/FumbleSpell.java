package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class FumbleSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Actor attacker = (Actor) context.resolveSingleTarget((EntityReference) context.getEnvironment().get(Environment.ATTACKER_REFERENCE));
		Actor randomTarget = context.getLogic().getAnotherRandomTarget(context.getActivePlayer(), attacker, (Actor) target,
				EntityReference.ENEMY_CHARACTERS);
		if (randomTarget != target) {
			context.getEnvironment().put(Environment.TARGET_OVERRIDE, randomTarget.getReference());
		}
	}

}
