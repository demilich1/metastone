package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
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
		Actor attacker = (Actor) context.getEnvironment().get(Environment.ATTACKER);
		Actor randomTarget = context.getLogic().getAnotherRandomTarget(player, attacker, (Actor) target, EntityReference.ALL_CHARACTERS);
		context.getEnvironment().put(Environment.TARGET_OVERRIDE, randomTarget);
	}
}
