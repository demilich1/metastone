package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class MisdirectSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(MisdirectSpell.class);
		desc.setTarget(EntityReference.EVENT_TARGET);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Actor attacker = (Actor) context.getEnvironment().get(Environment.ATTACKER);
		Actor randomTarget = context.getLogic().getAnotherRandomTarget(player, attacker, (Actor) target, EntityReference.ALL_CHARACTERS);
		context.getEnvironment().put(Environment.TARGET_OVERRIDE, randomTarget);
	}
}
