package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class LifeLeechSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(LifeLeechSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion attacker = (Minion) context.getEnvironment().get(Environment.ATTACKER);
		int healing = attacker.getAttack();
		Entity source = context.resolveSingleTarget(desc.getSourceEntity());
		context.getLogic().heal(player, (Actor) target, healing, source);
	}

}
