package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class SetHpSpell extends Spell {

	public static SpellDesc create(EntityReference target, int hp) {
		Map<SpellArg, Object> arguments = SpellDesc.build(SetHpSpell.class);
		arguments.put(SpellArg.VALUE, hp);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(int hp) {
		return create(null, hp);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int hp = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);
		Actor targetActor = (Actor) target;
		targetActor.removeAttribute(Attribute.HP_BONUS);
		context.getLogic().modifyMaxHp(targetActor, hp);
	}

}
