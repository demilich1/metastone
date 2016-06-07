package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class SwipeSpell extends Spell {

	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int primaryDamage = desc.getValue(SpellArg.VALUE, context, player, target, source, 2);
		int secondaryDamage = desc.getValue(SpellArg.SECONDARY_VALUE, context, player, target, source, 1);
		EntityReference targetKey = (EntityReference) desc.get(SpellArg.SECONDARY_TARGET);
		if (targetKey == null) {
			targetKey = EntityReference.ENEMY_CHARACTERS;
		}
		for (Entity entity : context.resolveTarget(player, target, targetKey)) {
			if (entity != target) {
				context.getLogic().damage(player, (Actor) entity, secondaryDamage, source);
			}
		}

		context.getLogic().damage(player, (Actor) target, primaryDamage, source);
	}

}
