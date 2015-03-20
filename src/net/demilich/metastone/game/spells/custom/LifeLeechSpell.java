package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class LifeLeechSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(LifeLeechSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.FRIENDLY_HERO);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Minion attacker = (Minion) context.getEnvironment().get(Environment.ATTACKER);
		int healing = attacker.getAttack();
		context.getLogic().heal(player, (Actor) target, healing, source);
	}

}
